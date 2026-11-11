package com.codeinsight.app.ui.analytics;

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
import com.codeinsight.app.domain.analysis.AnalyticsData;
import com.codeinsight.app.domain.model.CategoryCount;
import java.util.List;

public class AnalyticsViewModel extends AndroidViewModel {

    private final ProblemRepository repository;
    private final MediatorLiveData<AnalyticsData> analyticsDataLive = new MediatorLiveData<>();

    public AnalyticsViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();

        LiveData<PlatformConnection> activeAccountLive = repository.getActiveConnectionLive();
        LiveData<List<ProblemWithDetails>> problemsLive = repository.getAllProblemsWithDetails();
        LiveData<List<CategoryCount>> mistakeCountsLive = repository.getMistakeCategoryCounts();

        analyticsDataLive.addSource(activeAccountLive, account -> {
            recompute(problemsLive.getValue(), mistakeCountsLive.getValue(), account);
        });
        analyticsDataLive.addSource(problemsLive, problems -> {
            recompute(problems, mistakeCountsLive.getValue(), activeAccountLive.getValue());
        });
        analyticsDataLive.addSource(mistakeCountsLive, mistakes -> {
            recompute(problemsLive.getValue(), mistakes, activeAccountLive.getValue());
        });
    }

    public LiveData<AnalyticsData> getAnalyticsData() {
        return analyticsDataLive;
    }

    private void recompute(List<ProblemWithDetails> problems, List<CategoryCount> mistakeCounts, PlatformConnection account) {
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                AnalyticsData data = repository.computeAnalyticsData(problems, mistakeCounts, account);
                if (data != null) {
                    analyticsDataLive.postValue(data);
                }
            } catch (Exception e) {
                android.util.Log.e("AnalyticsVM", "Error computing analytics data", e);
            }
        });
    }
}


