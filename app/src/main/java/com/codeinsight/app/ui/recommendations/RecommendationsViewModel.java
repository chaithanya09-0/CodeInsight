package com.codeinsight.app.ui.recommendations;

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
import com.codeinsight.app.domain.analysis.GeneratedRecommendation;
import com.codeinsight.app.domain.analysis.TopicDiagnostic;
import java.util.ArrayList;
import java.util.List;

public class RecommendationsViewModel extends AndroidViewModel {

    private final ProblemRepository repository;
    private final MediatorLiveData<List<GeneratedRecommendation>> recommendationsLive = new MediatorLiveData<>();
    private final MediatorLiveData<List<TopicDiagnostic>> diagnosticsLive = new MediatorLiveData<>();

    public RecommendationsViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();

        LiveData<PlatformConnection> activeAccountLive = repository.getActiveConnectionLive();
        LiveData<List<ProblemWithDetails>> problemsLive = repository.getAllProblemsWithDetails();

        recommendationsLive.addSource(activeAccountLive, account -> {
            recomputeRecommendations(problemsLive.getValue(), account);
        });
        recommendationsLive.addSource(problemsLive, problems -> {
            recomputeRecommendations(problems, activeAccountLive.getValue());
        });

        diagnosticsLive.addSource(activeAccountLive, account -> {
            recomputeDiagnostics(problemsLive.getValue(), account);
        });
        diagnosticsLive.addSource(problemsLive, problems -> {
            recomputeDiagnostics(problems, activeAccountLive.getValue());
        });
    }

    private void recomputeRecommendations(List<ProblemWithDetails> problems, PlatformConnection account) {
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<GeneratedRecommendation> recs = repository.generateRecommendations(problems, account);
                recommendationsLive.postValue(recs != null ? recs : new ArrayList<>());
            } catch (Exception e) {
                android.util.Log.e("RecViewModel", "Error computing recommendations", e);
                recommendationsLive.postValue(new ArrayList<>());
            }
        });
    }

    private void recomputeDiagnostics(List<ProblemWithDetails> problems, PlatformConnection account) {
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<TopicDiagnostic> diags = repository.evaluateTopics(problems, account);
                diagnosticsLive.postValue(diags != null ? diags : new ArrayList<>());
            } catch (Exception e) {
                android.util.Log.e("RecViewModel", "Error computing diagnostics", e);
                diagnosticsLive.postValue(new ArrayList<>());
            }
        });
    }

    public LiveData<List<GeneratedRecommendation>> getRecommendations() {
        return recommendationsLive;
    }

    public LiveData<List<TopicDiagnostic>> getDiagnostics() {
        return diagnosticsLive;
    }

    public void dismissRecommendation(GeneratedRecommendation rec) {
        List<GeneratedRecommendation> current = recommendationsLive.getValue();
        if (current != null) {
            List<GeneratedRecommendation> updated = new ArrayList<>(current);
            updated.remove(rec);
            recommendationsLive.setValue(updated);
        }
    }
}


