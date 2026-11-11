package com.codeinsight.app.ui.dashboard;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.database.CodeInsightDatabase;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.domain.analysis.DashboardStats;
import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private final ProblemRepository repository;
    private final MediatorLiveData<DashboardStats> dashboardStats = new MediatorLiveData<>();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();

        LiveData<PlatformConnection> activeAccountLive = repository.getActiveConnectionLive();
        LiveData<List<ProblemWithDetails>> problemsLive = repository.getAllProblemsWithDetails();

        dashboardStats.addSource(activeAccountLive, account -> {
            recompute(problemsLive.getValue(), account);
        });

        dashboardStats.addSource(problemsLive, problems -> {
            recompute(problems, activeAccountLive.getValue());
        });
    }

    private void recompute(List<ProblemWithDetails> problems, PlatformConnection account) {
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                DashboardStats stats = repository.computeDashboardStats(problems, account);
                if (stats != null) {
                    dashboardStats.postValue(stats);
                }
            } catch (Exception e) {
                android.util.Log.e("DashboardVM", "Error computing dashboard stats", e);
            }
        });
    }

    public LiveData<DashboardStats> getDashboardStats() {
        return dashboardStats;
    }
}

