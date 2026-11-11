package com.codeinsight.app.ui.leetcode;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.database.CodeInsightDatabase;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.SyncHistory;
import com.codeinsight.app.data.preferences.UserPreferencesManager;
import com.codeinsight.app.data.remote.leetcode.LeetCodeProfile;
import com.codeinsight.app.data.remote.leetcode.LeetCodeSyncRepository;
import java.util.List;

public class LeetCodeViewModel extends AndroidViewModel {

    private final LeetCodeSyncRepository syncRepository;
    private final UserPreferencesManager preferencesManager;
    private final LiveData<List<PlatformConnection>> connectionsLive;
    private final LiveData<PlatformConnection> activeConnectionLive;
    private final LiveData<List<SyncHistory>> recentSyncHistory;

    private final MutableLiveData<LeetCodeProfile> profilePreview = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LeetCodeViewModel(@NonNull Application application) {
        super(application);
        CodeInsightApp app = (CodeInsightApp) application;
        syncRepository = app.getLeetCodeSyncRepository();
        preferencesManager = app.getPreferencesManager();
        connectionsLive = app.getDatabase().platformConnectionDao().getConnectionsByPlatformLive("LeetCode");
        activeConnectionLive = app.getDatabase().platformConnectionDao().getActiveConnectionLive("LeetCode");
        recentSyncHistory = app.getDatabase().syncHistoryDao().getRecentSyncHistory(5);
    }

    public LiveData<List<PlatformConnection>> getConnectionsLive() { return connectionsLive; }
    public LiveData<PlatformConnection> getActiveConnectionLive() { return activeConnectionLive; }
    public LiveData<PlatformConnection> getConnectionLive() { return activeConnectionLive; }
    public LiveData<List<SyncHistory>> getRecentSyncHistory() { return recentSyncHistory; }
    public LiveData<LeetCodeProfile> getProfilePreview() { return profilePreview; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getStatusMessage() { return statusMessage; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    public void switchActiveProfile(String username) {
        if (username == null || username.trim().isEmpty()) return;
        isLoading.setValue(true);
        CodeInsightApp app = (CodeInsightApp) getApplication();
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                app.getDatabase().platformConnectionDao().deactivateAll("LeetCode");
                app.getDatabase().platformConnectionDao().setActiveProfile("LeetCode", username.trim());
                PlatformConnection conn = app.getDatabase().platformConnectionDao().getConnection("LeetCode", username.trim());
                if (conn != null && preferencesManager != null) {
                    preferencesManager.setActiveLeetCodeUsername(conn.getUsername());
                    preferencesManager.saveLeetCodeStats(
                        conn.getTotalSolved(),
                        conn.getEasySolved(),
                        conn.getMediumSolved(),
                        conn.getHardSolved(),
                        conn.getRanking()
                    );
                }
                statusMessage.postValue("Switched active profile to @" + username);
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue("Failed to switch profile: " + e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void deleteProfile(String username) {
        if (username == null || username.trim().isEmpty()) return;
        isLoading.setValue(true);
        CodeInsightApp app = (CodeInsightApp) getApplication();
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                app.getDatabase().platformConnectionDao().deleteConnection("LeetCode", username.trim());
                // If there are other connections, activate the first one
                List<PlatformConnection> remaining = app.getDatabase().platformConnectionDao().getConnectionsByPlatformSync("LeetCode");
                if (remaining != null && !remaining.isEmpty()) {
                    PlatformConnection next = remaining.get(0);
                    app.getDatabase().platformConnectionDao().setActiveProfile("LeetCode", next.getUsername());
                    if (preferencesManager != null) {
                        preferencesManager.setActiveLeetCodeUsername(next.getUsername());
                        preferencesManager.saveLeetCodeStats(
                            next.getTotalSolved(),
                            next.getEasySolved(),
                            next.getMediumSolved(),
                            next.getHardSolved(),
                            next.getRanking()
                        );
                    }
                } else if (preferencesManager != null) {
                    preferencesManager.setActiveLeetCodeUsername("");
                    preferencesManager.saveLeetCodeStats(0, 0, 0, 0, 0);
                }
                statusMessage.postValue("Profile @" + username + " removed");
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue("Failed to remove profile: " + e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void checkProfile(String username) {
        if (username == null || username.trim().isEmpty()) {
            errorMessage.setValue("Please enter a LeetCode username");
            return;
        }

        isLoading.setValue(true);
        errorMessage.setValue(null);
        statusMessage.setValue(null);

        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                LeetCodeProfile profile = syncRepository.fetchProfileSync(username.trim());
                profilePreview.postValue(profile);
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue(e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void syncUserProblems(String username) {
        if (username == null || username.trim().isEmpty()) {
            errorMessage.setValue("Please enter a LeetCode username");
            return;
        }

        isLoading.setValue(true);
        errorMessage.setValue(null);
        statusMessage.setValue(null);

        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                LeetCodeProfile profile = syncRepository.fetchProfileSync(username.trim());
                profilePreview.postValue(profile);
                int count = syncRepository.syncUserProblemsSync(username.trim());
                if (count > 0) {
                    statusMessage.postValue("Synced " + count + " submissions for @" + username + "! " + profile.getTotalSolved() + " solved verified.");
                } else {
                    statusMessage.postValue("Profile @" + username + " connected! " + profile.getTotalSolved() + " solved problems verified.");
                }
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue(e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void clearMessages() {
        statusMessage.setValue(null);
        errorMessage.setValue(null);
    }
}


