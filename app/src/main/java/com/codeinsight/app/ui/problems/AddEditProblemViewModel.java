package com.codeinsight.app.ui.problems;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.data.repository.ProblemRepository;
import java.util.ArrayList;
import java.util.List;

public class AddEditProblemViewModel extends AndroidViewModel {

    private final ProblemRepository repository;
    private final LiveData<List<Topic>> allTopics;
    private final MutableLiveData<ProblemWithDetails> problemToEdit = new MutableLiveData<>();
    private final List<Long> selectedTopicIds = new ArrayList<>();

    public AddEditProblemViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();
        allTopics = repository.getAllTopics();
    }

    public LiveData<List<Topic>> getAllTopics() {
        return allTopics;
    }

    public LiveData<ProblemWithDetails> getProblemToEdit() {
        return problemToEdit;
    }

    public List<Long> getSelectedTopicIds() {
        return selectedTopicIds;
    }

    public void loadProblem(long problemId) {
        LiveData<ProblemWithDetails> live = repository.getProblemWithDetails(problemId);
        live.observeForever(pwd -> {
            if (pwd != null) {
                problemToEdit.setValue(pwd);
                selectedTopicIds.clear();
                for (Topic t : pwd.topics) {
                    selectedTopicIds.add(t.getId());
                }
            }
        });
    }

    public void toggleTopic(long topicId) {
        if (selectedTopicIds.contains(topicId)) {
            selectedTopicIds.remove(topicId);
        } else {
            selectedTopicIds.add(topicId);
        }
    }

    public void createTopic(String name, ProblemRepository.RepositoryCallback<Topic> callback) {
        repository.createTopic(name, "Custom", callback);
    }

    public void saveProblem(
        long existingId,
        String title,
        String url,
        String platform,
        String difficulty,
        String notes,
        boolean logInitialAttempt,
        boolean attemptAccepted,
        Integer durationMinutes,
        String language,
        String attemptNotes,
        ProblemRepository.RepositoryCallback<Long> callback
    ) {
        CodingProblem problem = new CodingProblem();
        problem.setId(existingId);
        problem.setTitle(title.trim());
        problem.setUrl(url != null ? url.trim() : "");
        problem.setPlatform(platform);
        problem.setDifficulty(difficulty);
        problem.setNotes(notes != null ? notes.trim() : "");

        CodingAttempt initialAttempt = null;
        if (logInitialAttempt && existingId <= 0) {
            initialAttempt = new CodingAttempt();
            initialAttempt.setAccepted(attemptAccepted);
            initialAttempt.setDurationMinutes(durationMinutes);
            initialAttempt.setLanguage(language != null && !language.trim().isEmpty() ? language.trim() : "Java");
            initialAttempt.setNotes(attemptNotes != null ? attemptNotes.trim() : "");
        }

        if (existingId > 0) {
            // Update
            ProblemWithDetails existing = problemToEdit.getValue();
            if (existing != null) {
                problem.setStatus(existing.problem.getStatus());
                problem.setTotalAttempts(existing.problem.getTotalAttempts());
                problem.setCreatedAt(existing.problem.getCreatedAt());
                problem.setExternalId(existing.problem.getExternalId());
            }
            repository.updateProblem(problem, selectedTopicIds, new ProblemRepository.RepositoryCallback<Void>() {
                @Override
                public void onComplete(Void result) {
                    if (callback != null) callback.onComplete(existingId);
                }

                @Override
                public void onError(Exception e) {
                    if (callback != null) callback.onError(e);
                }
            });
        } else {
            // Insert
            repository.insertProblem(problem, selectedTopicIds, initialAttempt, callback);
        }
    }
}

