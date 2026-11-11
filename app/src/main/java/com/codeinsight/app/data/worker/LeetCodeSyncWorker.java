package com.codeinsight.app.data.worker;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import java.util.concurrent.TimeUnit;

public class LeetCodeSyncWorker extends Worker {

    private static final String WORK_NAME = "leetcode_periodic_sync_java";

    public LeetCodeSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        CodeInsightApp app = (CodeInsightApp) getApplicationContext();
        boolean autoSync = app.getPreferencesManager().isAutoSync();
        if (!autoSync) {
            return Result.success();
        }

        PlatformConnection connection = app.getDatabase().platformConnectionDao().getConnectionByPlatform("LeetCode");
        if (connection == null || !connection.isConnected() || connection.getUsername() == null || connection.getUsername().trim().isEmpty()) {
            return Result.success();
        }

        try {
            app.getLeetCodeSyncRepository().syncUserProblemsSync(connection.getUsername());
            return Result.success();
        } catch (Exception e) {
            return Result.retry();
        }
    }

    public static void schedule(Context context) {
        Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(LeetCodeSyncWorker.class, 6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        );
    }

    public static void cancel(Context context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME);
    }
}

