package com.codeinsight.app;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.codeinsight.app.data.local.database.CodeInsightDatabase;
import com.codeinsight.app.data.preferences.UserPreferencesManager;
import com.codeinsight.app.data.remote.leetcode.LeetCodeApiService;
import com.codeinsight.app.data.remote.leetcode.LeetCodeSyncRepository;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.data.worker.LeetCodeSyncWorker;

public class CodeInsightApp extends Application {

    private CodeInsightDatabase database;
    private UserPreferencesManager preferencesManager;
    private ProblemRepository problemRepository;
    private LeetCodeSyncRepository leetCodeSyncRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            database = CodeInsightDatabase.getInstance(this);
            preferencesManager = new UserPreferencesManager(this);
            problemRepository = new ProblemRepository(this);

            LeetCodeApiService apiService = LeetCodeApiService.Factory.create();
            leetCodeSyncRepository = new LeetCodeSyncRepository(
                apiService,
                database.problemDao(),
                database.attemptDao(),
                database.mistakeDao(),
                database.topicDao(),
                database.platformConnectionDao(),
                database.syncHistoryDao(),
                preferencesManager
            );

            // Ensure standard topics exist and normalize existing database records safely
            CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
                try {
                    CodeInsightDatabase.normalizeAndMergeExistingTopics(database.topicDao());
                } catch (Exception e) {
                    android.util.Log.e("CodeInsightApp", "Error normalizing topics", e);
                }
            });

            // Apply theme preference
            boolean isDark = preferencesManager.isDarkTheme();
            AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );

            // Schedule periodic sync if enabled
            if (preferencesManager.isAutoSync()) {
                try {
                    LeetCodeSyncWorker.schedule(this);
                } catch (Exception e) {
                    android.util.Log.w("CodeInsightApp", "WorkManager schedule deferred", e);
                }
            }
        } catch (Exception e) {
            android.util.Log.e("CodeInsightApp", "Fatal startup error intercepted", e);
        }
    }

    public CodeInsightDatabase getDatabase() {
        return database;
    }

    public UserPreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public ProblemRepository getProblemRepository() {
        return problemRepository;
    }

    public LeetCodeSyncRepository getLeetCodeSyncRepository() {
        return leetCodeSyncRepository;
    }
}

