package com.codeinsight.app.ui.mistakes;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.domain.model.MistakeCategory;
import java.util.List;

public class MistakeViewModel extends AndroidViewModel {

    private final ProblemRepository repository;

    public MistakeViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();
    }

    public LiveData<ProblemWithDetails> getProblemWithDetails(long problemId) {
        return repository.getProblemWithDetails(problemId);
    }

    public LiveData<List<Mistake>> getMistakesForProblem(long problemId) {
        return repository.getMistakesForProblem(problemId);
    }

    public void logMistakes(
        long problemId,
        long attemptId,
        List<MistakeCategory> categories,
        String description,
        String notes,
        ProblemRepository.RepositoryCallback<Void> callback
    ) {
        repository.logMistakes(problemId, attemptId, categories, description, notes, callback);
    }

    public void deleteMistake(Mistake mistake, ProblemRepository.RepositoryCallback<Void> callback) {
        repository.deleteMistake(mistake, callback);
    }
}

