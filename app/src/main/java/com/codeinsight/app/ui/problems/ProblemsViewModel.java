package com.codeinsight.app.ui.problems;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.domain.model.Difficulty;
import com.codeinsight.app.domain.model.Platform;
import com.codeinsight.app.domain.model.ProblemStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProblemsViewModel extends AndroidViewModel {

    private final ProblemRepository repository;
    private final LiveData<List<ProblemWithDetails>> allProblems;
    private final LiveData<PlatformConnection> activeConnection;
    private final MediatorLiveData<List<ProblemWithDetails>> filteredProblems = new MediatorLiveData<>();

    private String searchQuery = "";
    private Platform filterPlatform = null;
    private Difficulty filterDifficulty = null;
    private ProblemStatus filterStatus = null;
    private int sortOrder = 0; // 0=Newest, 1=Oldest, 2=Title, 3=Diff Easy-Hard, 4=Most Attempts

    public ProblemsViewModel(@NonNull Application application) {
        super(application);
        repository = ((CodeInsightApp) application).getProblemRepository();
        allProblems = repository.getAllProblemsWithDetails();
        activeConnection = repository.getActiveConnectionLive();

        filteredProblems.addSource(allProblems, problems -> applyFilters());
        filteredProblems.addSource(activeConnection, conn -> applyFilters());
    }

    public LiveData<List<ProblemWithDetails>> getFilteredProblems() {
        return filteredProblems;
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query != null ? query : "";
        applyFilters();
    }

    public void setFilterPlatform(Platform platform) {
        this.filterPlatform = platform;
        applyFilters();
    }

    public void setFilterDifficulty(Difficulty difficulty) {
        this.filterDifficulty = difficulty;
        applyFilters();
    }

    public void setFilterStatus(ProblemStatus status) {
        this.filterStatus = status;
        applyFilters();
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        applyFilters();
    }

    public Platform getFilterPlatform() { return filterPlatform; }
    public Difficulty getFilterDifficulty() { return filterDifficulty; }
    public ProblemStatus getFilterStatus() { return filterStatus; }
    public int getSortOrder() { return sortOrder; }

    public void deleteProblem(CodingProblem problem, ProblemRepository.RepositoryCallback<Void> callback) {
        repository.deleteProblem(problem, callback);
    }

    private void applyFilters() {
        List<ProblemWithDetails> source = allProblems.getValue();
        if (source == null) {
            filteredProblems.setValue(new ArrayList<>());
            return;
        }

        PlatformConnection conn = activeConnection != null ? activeConnection.getValue() : null;
        String activeUser = (conn != null && conn.getUsername() != null) ? conn.getUsername().trim().toLowerCase() : null;

        List<ProblemWithDetails> result = new ArrayList<>();
        String queryLower = searchQuery.trim().toLowerCase();

        for (ProblemWithDetails p : source) {
            if (p == null || p.problem == null) continue;

            // Partition by active account
            String pUser = p.problem.getAccountUsername();
            String platform = p.problem.getPlatform();

            if (activeUser != null && !activeUser.isEmpty()) {
                // If problem is from LeetCode, it MUST match the active user
                if ("LeetCode".equalsIgnoreCase(platform)) {
                    if (pUser == null || !pUser.equalsIgnoreCase(activeUser)) {
                        continue; // Skip LeetCode problems not belonging to this active profile
                    }
                } else {
                    // Manual or other platforms: only allow if owned by this user or unassigned
                    if (pUser != null && !pUser.trim().isEmpty() && !pUser.equalsIgnoreCase(activeUser)) {
                        continue;
                    }
                }
            } else {
                // No active account connected: only show manual non-LeetCode problems
                if ("LeetCode".equalsIgnoreCase(platform) || (pUser != null && !pUser.trim().isEmpty())) {
                    continue;
                }
            }

            // Search query filter
            boolean matchesQuery = queryLower.isEmpty()
                || (p.problem.getTitle() != null && p.problem.getTitle().toLowerCase().contains(queryLower))
                || (p.problem.getNotes() != null && p.problem.getNotes().toLowerCase().contains(queryLower));

            if (!matchesQuery && p.topics != null) {
                for (Topic t : p.topics) {
                    if (t.getName() != null && t.getName().toLowerCase().contains(queryLower)) {
                        matchesQuery = true;
                        break;
                    }
                }
            }

            if (!matchesQuery) continue;

            // Platform filter
            if (filterPlatform != null) {
                Platform pPlatform = Platform.fromString(p.problem.getPlatform());
                if (pPlatform != filterPlatform) continue;
            }

            // Difficulty filter
            if (filterDifficulty != null) {
                Difficulty pDiff = Difficulty.fromString(p.problem.getDifficulty());
                if (pDiff != filterDifficulty) continue;
            }

            // Status filter
            if (filterStatus != null) {
                ProblemStatus pStatus = ProblemStatus.fromString(p.problem.getStatus());
                if (pStatus != filterStatus) continue;
            }

            result.add(p);
        }

        // Sorting
        Collections.sort(result, (a, b) -> {
            switch (sortOrder) {
                case 1: // Oldest First
                    return Long.compare(a.problem.getCreatedAt(), b.problem.getCreatedAt());
                case 2: // Title A-Z
                    String titleA = a.problem.getTitle() != null ? a.problem.getTitle() : "";
                    String titleB = b.problem.getTitle() != null ? b.problem.getTitle() : "";
                    return titleA.compareToIgnoreCase(titleB);
                case 3: // Difficulty Easy-Hard
                    int rankA = getDiffRank(a.problem.getDifficulty());
                    int rankB = getDiffRank(b.problem.getDifficulty());
                    return Integer.compare(rankA, rankB);
                case 4: // Most attempts
                    return Integer.compare(b.problem.getTotalAttempts(), a.problem.getTotalAttempts());
                default: // Newest First (updatedAt desc)
                    return Long.compare(b.problem.getUpdatedAt(), a.problem.getUpdatedAt());
            }
        });

        filteredProblems.setValue(result);
    }

    private int getDiffRank(String diff) {
        if ("Easy".equalsIgnoreCase(diff)) return 1;
        if ("Medium".equalsIgnoreCase(diff)) return 2;
        if ("Hard".equalsIgnoreCase(diff)) return 3;
        return 4;
    }
}
