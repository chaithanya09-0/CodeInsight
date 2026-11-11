package com.codeinsight.app.ui.problems;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.repository.ProblemRepository;

public class ProblemDetailViewModel extends AndroidViewModel {

    private final ProblemRepository repository;

    public ProblemDetailViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();
    }

    public LiveData<ProblemWithDetails> getProblemWithDetails(long problemId) {
        return repository.getProblemWithDetails(problemId);
    }

    public void logAttempt(
        long problemId,
        boolean accepted,
        Integer durationMinutes,
        String language,
        String notes,
        ProblemRepository.RepositoryCallback<Long> callback
    ) {
        repository.logAttempt(problemId, accepted, durationMinutes, language, notes, callback);
    }

    public void deleteProblem(CodingProblem problem, ProblemRepository.RepositoryCallback<Void> callback) {
        repository.deleteProblem(problem, callback);
    }
}

