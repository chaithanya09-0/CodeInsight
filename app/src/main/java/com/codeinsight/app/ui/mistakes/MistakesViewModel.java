package com.codeinsight.app.ui.mistakes;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.MistakeWithProblem;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.domain.model.CategoryCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MistakesViewModel extends AndroidViewModel {

    private final ProblemRepository repository;
    private final LiveData<List<MistakeWithProblem>> allMistakes;
    private final LiveData<List<ProblemWithDetails>> allProblems;
    private final LiveData<PlatformConnection> activeConnection;
    private final MediatorLiveData<List<MistakeWithProblem>> filteredMistakes = new MediatorLiveData<>();

    private final MutableLiveData<String> dominantCategoryLive = new MutableLiveData<>("None");
    private final MutableLiveData<Integer> totalMistakesCountLive = new MutableLiveData<>(0);

    private String selectedCategory = "ALL";
    private String selectedTopic = "ALL";
    private String searchQuery = "";

    public MistakesViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();
        allMistakes = repository.getAllMistakesWithProblem();
        allProblems = repository.getAllProblemsWithDetails();
        activeConnection = repository.getActiveConnectionLive();

        filteredMistakes.addSource(allMistakes, mistakes -> applyFilter());
        filteredMistakes.addSource(allProblems, problems -> applyFilter());
        filteredMistakes.addSource(activeConnection, conn -> applyFilter());
    }

    public LiveData<List<MistakeWithProblem>> getFilteredMistakes() {
        return filteredMistakes;
    }

    public LiveData<List<ProblemWithDetails>> getAllProblems() {
        return allProblems;
    }

    public LiveData<String> getDominantCategoryLive() {
        return dominantCategoryLive;
    }

    public LiveData<Integer> getTotalMistakesCountLive() {
        return totalMistakesCountLive;
    }

    public void setSelectedCategory(String category) {
        this.selectedCategory = category != null ? category : "ALL";
        applyFilter();
    }

    public void setSelectedTopic(String topic) {
        this.selectedTopic = topic != null ? topic : "ALL";
        applyFilter();
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query != null ? query : "";
        applyFilter();
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public String getSelectedTopic() {
        return selectedTopic;
    }

    public void deleteMistake(Mistake mistake, ProblemRepository.RepositoryCallback<Void> callback) {
        repository.deleteMistake(mistake, callback);
    }

    public void updateMistake(Mistake mistake, ProblemRepository.RepositoryCallback<Void> callback) {
        repository.updateMistake(mistake, callback);
    }

    private void applyFilter() {
        List<MistakeWithProblem> source = allMistakes.getValue();
        if (source == null) {
            filteredMistakes.setValue(new ArrayList<>());
            totalMistakesCountLive.setValue(0);
            dominantCategoryLive.setValue("None");
            return;
        }

        PlatformConnection conn = activeConnection != null ? activeConnection.getValue() : null;
        String activeUser = (conn != null && conn.getUsername() != null) ? conn.getUsername().trim().toLowerCase() : null;

        // Filter source by active account
        List<MistakeWithProblem> accountFilteredSource = new ArrayList<>();
        for (MistakeWithProblem mwp : source) {
            if (mwp == null || mwp.mistake == null) continue;
            if (mwp.problem != null) {
                String pUser = mwp.problem.getAccountUsername();
                String platform = mwp.problem.getPlatform();
                if (activeUser != null && !activeUser.isEmpty()) {
                    if ("LeetCode".equalsIgnoreCase(platform)) {
                        if (pUser == null || !pUser.equalsIgnoreCase(activeUser)) continue;
                    } else {
                        if (pUser != null && !pUser.trim().isEmpty() && !pUser.equalsIgnoreCase(activeUser)) continue;
                    }
                } else {
                    if ("LeetCode".equalsIgnoreCase(platform) || (pUser != null && !pUser.trim().isEmpty())) continue;
                }
            }
            accountFilteredSource.add(mwp);
        }

        totalMistakesCountLive.setValue(accountFilteredSource.size());

        // Calculate dominant category
        Map<String, Integer> counts = new HashMap<>();
        for (MistakeWithProblem mwp : accountFilteredSource) {
            if (mwp.mistake != null && mwp.mistake.getCategory() != null) {
                String cat = mwp.mistake.getCategory();
                counts.put(cat, counts.getOrDefault(cat, 0) + 1);
            }
        }
        String topCat = "None";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topCat = entry.getKey() + " (" + maxCount + "x)";
            }
        }
        dominantCategoryLive.setValue(topCat);

        // Build problemId -> topic names map
        Map<Long, List<String>> problemTopicsMap = new HashMap<>();
        List<ProblemWithDetails> problemsList = allProblems.getValue();
        if (problemsList != null) {
            for (ProblemWithDetails p : problemsList) {
                List<String> tNames = new ArrayList<>();
                if (p.topics != null) {
                    for (com.codeinsight.app.data.local.entity.Topic t : p.topics) {
                        tNames.add(t.getName());
                    }
                }
                problemTopicsMap.put(p.problem.getId(), tNames);
            }
        }

        List<MistakeWithProblem> result = new ArrayList<>();
        String queryLower = searchQuery.trim().toLowerCase();

        for (MistakeWithProblem item : accountFilteredSource) {
            // Category filter
            if (!"ALL".equalsIgnoreCase(selectedCategory)) {
                if (item.mistake.getCategory() == null ||
                    !item.mistake.getCategory().equalsIgnoreCase(selectedCategory)) {
                    continue;
                }
            }

            // Topic filter
            if (!"ALL".equalsIgnoreCase(selectedTopic)) {
                List<String> tNames = problemTopicsMap.get(item.mistake.getProblemId());
                if (tNames == null || !tNames.contains(selectedTopic)) {
                    continue;
                }
            }

            // Search query filter (search across problem title, mistake description, and reflection notes)
            if (!queryLower.isEmpty()) {
                boolean match = false;
                if (item.problem != null && item.problem.getTitle() != null &&
                    item.problem.getTitle().toLowerCase().contains(queryLower)) {
                    match = true;
                }
                if (!match && item.mistake.getDescription() != null &&
                    item.mistake.getDescription().toLowerCase().contains(queryLower)) {
                    match = true;
                }
                if (!match && item.mistake.getNotes() != null &&
                    item.mistake.getNotes().toLowerCase().contains(queryLower)) {
                    match = true;
                }
                if (!match) continue;
            }

            result.add(item);
        }

        filteredMistakes.setValue(result);
    }
}
