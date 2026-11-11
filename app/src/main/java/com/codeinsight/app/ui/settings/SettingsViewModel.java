package com.codeinsight.app.ui.settings;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.database.CodeInsightDatabase;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.data.preferences.UserPreferencesManager;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.data.worker.LeetCodeSyncWorker;
import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

    private final UserPreferencesManager preferencesManager;
    private final ProblemRepository repository;
    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private final MutableLiveData<String> exportJsonLive = new MutableLiveData<>();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        CodeInsightApp app = (CodeInsightApp) application;
        preferencesManager = app.getPreferencesManager();
        repository = app.getProblemRepository();
    }

    public LiveData<Boolean> getDarkThemeLive() { return preferencesManager.getDarkThemeLive(); }
    public LiveData<String> getUserNameLive() { return preferencesManager.getUserNameLive(); }
    public LiveData<Boolean> getAutoSyncLive() { return preferencesManager.getAutoSyncLive(); }
    public LiveData<String> getStatusMessage() { return statusMessage; }
    public LiveData<String> getExportJsonLive() { return exportJsonLive; }

    public void setUserName(String name) {
        preferencesManager.setUserName(name != null ? name.trim() : "");
    }

    public void setDarkTheme(boolean enabled) {
        preferencesManager.setDarkTheme(enabled);
        AppCompatDelegate.setDefaultNightMode(
            enabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    public void setAutoSync(Context context, boolean enabled) {
        preferencesManager.setAutoSync(enabled);
        if (enabled) {
            LeetCodeSyncWorker.schedule(context.getApplicationContext());
        } else {
            LeetCodeSyncWorker.cancel(context.getApplicationContext());
        }
    }

    public void seedDemoData() {
        repository.seedDemoData(new ProblemRepository.RepositoryCallback<Void>() {
            @Override
            public void onComplete(Void result) {
                statusMessage.postValue("Sample practice data populated successfully!");
            }

            @Override
            public void onError(Exception e) {
                statusMessage.postValue("Failed to populate demo data: " + e.getMessage());
            }
        });
    }

    public void clearAllData() {
        repository.clearAllData(new ProblemRepository.RepositoryCallback<Void>() {
            @Override
            public void onComplete(Void result) {
                statusMessage.postValue("All local data has been successfully cleared.");
            }

            @Override
            public void onError(Exception e) {
                statusMessage.postValue("Failed to clear data: " + e.getMessage());
            }
        });
    }

    public void exportDataAsJson() {
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<ProblemWithDetails> problems = CodeInsightDatabase.getInstance(getApplication())
                    .problemDao().getAllProblemsWithDetailsSync();

                StringBuilder sb = new StringBuilder();
                sb.append("{\n");
                sb.append("  \"exportedAt\": ").append(System.currentTimeMillis()).append(",\n");
                sb.append("  \"user\": \"").append(preferencesManager.getUserName()).append("\",\n");
                sb.append("  \"problemsCount\": ").append(problems.size()).append(",\n");
                sb.append("  \"problems\": [\n");

                for (int i = 0; i < problems.size(); i++) {
                    ProblemWithDetails p = problems.get(i);
                    sb.append("    {\n");
                    sb.append("      \"id\": ").append(p.problem.getId()).append(",\n");
                    sb.append("      \"title\": \"").append(escapeJson(p.problem.getTitle())).append("\",\n");
                    sb.append("      \"platform\": \"").append(p.problem.getPlatform()).append("\",\n");
                    sb.append("      \"difficulty\": \"").append(p.problem.getDifficulty()).append("\",\n");
                    sb.append("      \"status\": \"").append(p.problem.getStatus()).append("\",\n");
                    sb.append("      \"attemptsCount\": ").append(p.attempts.size()).append(",\n");
                    sb.append("      \"mistakesCount\": ").append(p.mistakes.size()).append(",\n");
                    sb.append("      \"topics\": [");
                    for (int j = 0; j < p.topics.size(); j++) {
                        sb.append("\"").append(p.topics.get(j).getName()).append("\"");
                        if (j < p.topics.size() - 1) sb.append(", ");
                    }
                    sb.append("]\n");
                    sb.append("    }").append(i < problems.size() - 1 ? ",\n" : "\n");
                }

                sb.append("  ]\n");
                sb.append("}");

                exportJsonLive.postValue(sb.toString());
            } catch (Exception e) {
                statusMessage.postValue("Export failed: " + e.getMessage());
            }
        });
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

