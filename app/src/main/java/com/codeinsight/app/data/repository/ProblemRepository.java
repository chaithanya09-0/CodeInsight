package com.codeinsight.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.codeinsight.app.data.local.dao.AttemptDao;
import com.codeinsight.app.data.local.dao.MistakeDao;
import com.codeinsight.app.data.local.dao.PlatformConnectionDao;
import com.codeinsight.app.data.local.dao.ProblemDao;
import com.codeinsight.app.data.local.dao.RecommendationDao;
import com.codeinsight.app.data.local.dao.SyncHistoryDao;
import com.codeinsight.app.data.local.dao.TopicDao;
import com.codeinsight.app.data.local.database.CodeInsightDatabase;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.MistakeWithProblem;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.ProblemTopicCrossRef;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Recommendation;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.domain.analysis.AnalyticsData;
import com.codeinsight.app.domain.analysis.DashboardStats;
import com.codeinsight.app.domain.analysis.DatePoint;
import com.codeinsight.app.domain.analysis.DayActivity;
import com.codeinsight.app.domain.analysis.GeneratedRecommendation;
import com.codeinsight.app.domain.analysis.MonthProgress;
import com.codeinsight.app.domain.analysis.TopicDiagnostic;
import com.codeinsight.app.domain.analysis.TopicPerformance;
import com.codeinsight.app.domain.analysis.WeakAreaAnalysisEngine;
import com.codeinsight.app.domain.analysis.WeakTopicSummary;
import com.codeinsight.app.domain.model.CategoryCount;
import com.codeinsight.app.domain.model.Difficulty;
import com.codeinsight.app.domain.model.MistakeCategory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

public class ProblemRepository {

    private final ProblemDao problemDao;
    private final AttemptDao attemptDao;
    private final MistakeDao mistakeDao;
    private final TopicDao topicDao;
    private final RecommendationDao recommendationDao;
    private final PlatformConnectionDao platformConnectionDao;
    private final ExecutorService executor;
    private final WeakAreaAnalysisEngine analysisEngine;
    private final com.codeinsight.app.data.preferences.UserPreferencesManager preferencesManager;

    public interface RepositoryCallback<T> {
        default void onComplete(T result) {
            onSuccess(result);
        }
        default void onSuccess(T result) {
        }
        void onError(Exception e);
    }

    public ProblemRepository(Application application) {
        CodeInsightDatabase db = CodeInsightDatabase.getInstance(application);
        this.problemDao = db.problemDao();
        this.attemptDao = db.attemptDao();
        this.mistakeDao = db.mistakeDao();
        this.topicDao = db.topicDao();
        this.recommendationDao = db.recommendationDao();
        this.platformConnectionDao = db.platformConnectionDao();
        this.executor = CodeInsightDatabase.databaseWriteExecutor;
        this.analysisEngine = new WeakAreaAnalysisEngine();
        this.preferencesManager = new com.codeinsight.app.data.preferences.UserPreferencesManager(application);
    }

    public LiveData<PlatformConnection> getActiveConnectionLive() {
        return platformConnectionDao.getActiveConnectionLive("LeetCode");
    }

    public PlatformConnection getActiveConnectionSync() {
        return platformConnectionDao.getActiveConnectionSync("LeetCode");
    }

    public LiveData<List<PlatformConnection>> getAllConnectionsLive() {
        return platformConnectionDao.getConnectionsByPlatformLive("LeetCode");
    }

    public LiveData<List<CodingProblem>> getAllProblems() {
        return problemDao.getAllProblems();
    }

    public LiveData<List<ProblemWithDetails>> getAllProblemsWithDetails() {
        return problemDao.getAllProblemsWithDetails();
    }

    public LiveData<List<ProblemWithDetails>> getProblemsForAccountWithDetails(String account) {
        return problemDao.getProblemsForAccountWithDetails(account);
    }

    public LiveData<ProblemWithDetails> getProblemWithDetails(long id) {
        return problemDao.getProblemWithDetails(id);
    }

    public LiveData<List<Topic>> getAllTopics() {
        return topicDao.getAllTopics();
    }

    public LiveData<List<Mistake>> getMistakesForProblem(long problemId) {
        return mistakeDao.getMistakesForProblem(problemId);
    }

    public LiveData<List<MistakeWithProblem>> getAllMistakesWithProblem() {
        return mistakeDao.getAllMistakesWithProblem();
    }

    public LiveData<List<CategoryCount>> getMistakeCategoryCounts() {
        return mistakeDao.getMistakeCategoryCounts();
    }

    public LiveData<List<Recommendation>> getActiveRecommendations() {
        return recommendationDao.getActiveRecommendations();
    }

    public void insertProblem(
        CodingProblem problem,
        List<Long> topicIds,
        CodingAttempt initialAttempt,
        RepositoryCallback<Long> callback
    ) {
        executor.execute(() -> {
            try {
                if (initialAttempt != null) {
                    problem.setStatus(initialAttempt.isAccepted() ? "Solved" : "Attempted");
                    problem.setTotalAttempts(problem.getTotalAttempts() + 1);
                }
                long problemId = problemDao.insert(problem);

                if (topicIds != null) {
                    for (Long tId : topicIds) {
                        topicDao.insertProblemTopicCrossRef(new ProblemTopicCrossRef(problemId, tId));
                    }
                }

                if (initialAttempt != null) {
                    initialAttempt.setProblemId(problemId);
                    attemptDao.insert(initialAttempt);
                }

                if (callback != null) callback.onComplete(problemId);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void updateProblem(
        CodingProblem problem,
        List<Long> topicIds,
        RepositoryCallback<Void> callback
    ) {
        executor.execute(() -> {
            try {
                problem.setUpdatedAt(System.currentTimeMillis());
                problemDao.update(problem);

                if (topicIds != null) {
                    topicDao.deleteTopicsForProblem(problem.getId());
                    for (Long tId : topicIds) {
                        topicDao.insertProblemTopicCrossRef(new ProblemTopicCrossRef(problem.getId(), tId));
                    }
                }

                if (callback != null) callback.onComplete(null);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void deleteProblem(CodingProblem problem, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                problemDao.delete(problem);
                if (callback != null) callback.onComplete(null);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void logAttempt(
        long problemId,
        boolean accepted,
        Integer durationMinutes,
        String language,
        String notes,
        RepositoryCallback<Long> callback
    ) {
        executor.execute(() -> {
            try {
                CodingProblem problem = problemDao.getProblemById(problemId);
                if (problem == null) throw new Exception("Problem not found");

                CodingAttempt attempt = new CodingAttempt();
                attempt.setProblemId(problemId);
                attempt.setAccepted(accepted);
                attempt.setDurationMinutes(durationMinutes);
                attempt.setLanguage(language);
                attempt.setNotes(notes);
                long attemptId = attemptDao.insert(attempt);

                if (accepted) {
                    problem.setStatus("Solved");
                } else if (!"Solved".equalsIgnoreCase(problem.getStatus())) {
                    problem.setStatus("Attempted");
                }
                problem.setTotalAttempts(problem.getTotalAttempts() + 1);
                problem.setUpdatedAt(System.currentTimeMillis());
                problemDao.update(problem);

                if (callback != null) callback.onComplete(attemptId);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void logMistakes(
        long problemId,
        long attemptId,
        List<MistakeCategory> categories,
        String description,
        String notes,
        RepositoryCallback<Void> callback
    ) {
        executor.execute(() -> {
            try {
                long targetAttemptId = attemptId;
                if (targetAttemptId <= 0) {
                    CodingAttempt autoAttempt = new CodingAttempt();
                    autoAttempt.setProblemId(problemId);
                    autoAttempt.setAccepted(false);
                    autoAttempt.setNotes("Attempt created for logged mistakes");
                    targetAttemptId = attemptDao.insert(autoAttempt);

                    CodingProblem problem = problemDao.getProblemById(problemId);
                    if (problem != null) {
                        if (!"Solved".equalsIgnoreCase(problem.getStatus())) {
                            problem.setStatus("Attempted");
                        }
                        problem.setTotalAttempts(problem.getTotalAttempts() + 1);
                        problem.setUpdatedAt(System.currentTimeMillis());
                        problemDao.update(problem);
                    }
                } else {
                    CodingProblem problem = problemDao.getProblemById(problemId);
                    if (problem != null) {
                        problem.setUpdatedAt(System.currentTimeMillis());
                        problemDao.update(problem);
                    }
                }

                for (MistakeCategory cat : categories) {
                    Mistake mistake = new Mistake();
                    mistake.setProblemId(problemId);
                    mistake.setAttemptId(targetAttemptId);
                    mistake.setCategory(cat.getDisplayName());
                    mistake.setDescription(description);
                    mistake.setNotes(notes);
                    mistakeDao.insert(mistake);
                }

                if (callback != null) callback.onComplete(null);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void deleteMistake(Mistake mistake, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                mistakeDao.delete(mistake);
                if (callback != null) callback.onComplete(null);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void updateMistake(Mistake mistake, RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                mistakeDao.update(mistake);
                if (callback != null) callback.onComplete(null);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void dismissRecommendation(long id) {
        executor.execute(() -> recommendationDao.dismissRecommendation(id));
    }

    public void seedDemoData(RepositoryCallback<Void> callback) {
        if (callback != null) callback.onComplete(null);
    }

    public void seedDemoDataSync() {
        // Strictly disabled to prevent synthetic demo data injection
    }

    public void autoGeneratePortfolio(RepositoryCallback<Void> callback) {
        if (callback != null) callback.onComplete(null);
    }

    public void clearAllData(RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            try {
                problemDao.deleteAllProblems();
                recommendationDao.deleteAllRecommendations();
                problemDao.purgeSyntheticDemoProblems();
                problemDao.purgeOrphanedMistakes();
                problemDao.purgeOrphanedAttempts();
                problemDao.purgeOrphanedCrossRefs();
                if (callback != null) callback.onComplete(null);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public void createTopic(String name, String category, RepositoryCallback<Topic> callback) {
        executor.execute(() -> {
            try {
                String normalized = com.codeinsight.app.domain.model.TopicNormalizer.normalize(name);
                if (normalized == null) {
                    throw new IllegalArgumentException("Cannot use platform or generic terms as topic name");
                }
                Topic existing = topicDao.getTopicByName(normalized);
                if (existing != null) {
                    if (callback != null) callback.onComplete(existing);
                    return;
                }
                Topic newTopic = new Topic(normalized, category);
                long id = topicDao.insert(newTopic);
                newTopic.setId(id);
                if (callback != null) callback.onComplete(newTopic);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
        });
    }

    public static Map<String, Integer> parseTagCounts(String raw) {
        Map<String, Integer> map = new HashMap<>();
        if (raw != null && !raw.trim().isEmpty()) {
            String[] pairs = raw.split(";");
            for (String p : pairs) {
                String[] kv = p.split("=");
                if (kv.length == 2) {
                    try {
                        map.put(kv[0].trim(), Integer.parseInt(kv[1].trim()));
                    } catch (Exception ignored) {}
                }
            }
        }
        return map;
    }

    public static List<ProblemWithDetails> filterProblemsForAccount(List<ProblemWithDetails> problems, String activeUser) {
        List<ProblemWithDetails> accountProblems = new ArrayList<>();
        if (problems == null) return accountProblems;
        for (ProblemWithDetails p : problems) {
            if (p == null || p.problem == null) continue;
            String pUser = p.problem.getAccountUsername();
            String platform = p.problem.getPlatform();

            if (activeUser != null && !activeUser.isEmpty()) {
                if ("LeetCode".equalsIgnoreCase(platform)) {
                    if (pUser == null || !pUser.equalsIgnoreCase(activeUser)) {
                        continue;
                    }
                } else {
                    if (pUser != null && !pUser.trim().isEmpty() && !pUser.equalsIgnoreCase(activeUser)) {
                        continue;
                    }
                }
            } else {
                if ("LeetCode".equalsIgnoreCase(platform) || (pUser != null && !pUser.trim().isEmpty())) {
                    continue;
                }
            }
            accountProblems.add(p);
        }
        return accountProblems;
    }

    public DashboardStats computeDashboardStats(List<ProblemWithDetails> problems) {
        return computeDashboardStats(problems, getActiveConnectionSync());
    }

    public DashboardStats computeDashboardStats(List<ProblemWithDetails> problems, PlatformConnection activeAccount) {
        DashboardStats stats = new DashboardStats();

        String activeUser = (activeAccount != null && activeAccount.getUsername() != null)
                ? activeAccount.getUsername().trim().toLowerCase() : "";

        // Filter problems strictly to active account (+ unassigned manual problems)
        List<ProblemWithDetails> accountProblems = filterProblemsForAccount(problems, activeUser);

        int localSolved = 0;
        int localEasySolved = 0, localMedSolved = 0, localHardSolved = 0;
        int localEasyTotal = 0, localMedTotal = 0, localHardTotal = 0;
        int totalAttempts = 0;
        int acceptedAttempts = 0;
        int failedAttempts = 0;
        double durationSum = 0;
        int durationCount = 0;

        List<CodingAttempt> allAttempts = new ArrayList<>();

        for (ProblemWithDetails p : accountProblems) {
            boolean isSolved = p.isSolved();
            if (isSolved) localSolved++;

            Difficulty diff = Difficulty.fromString(p.problem.getDifficulty());
            if (diff == Difficulty.EASY) {
                localEasyTotal++;
                if (isSolved) localEasySolved++;
            } else if (diff == Difficulty.MEDIUM) {
                localMedTotal++;
                if (isSolved) localMedSolved++;
            } else if (diff == Difficulty.HARD) {
                localHardTotal++;
                if (isSolved) localHardSolved++;
            }

            for (CodingAttempt att : p.attempts) {
                totalAttempts++;
                if (att.isAccepted()) {
                    acceptedAttempts++;
                    if (att.getDurationMinutes() != null && att.getDurationMinutes() > 0) {
                        durationSum += att.getDurationMinutes();
                        durationCount++;
                    }
                } else {
                    failedAttempts++;
                }
                allAttempts.add(att);
            }
        }

        // Synchronize telemetry with active account
        if (activeAccount != null && activeAccount.isConnected() && activeAccount.getTotalSolved() > 0) {
            stats.totalSolved = activeAccount.getTotalSolved();
            stats.easySolved = activeAccount.getEasySolved();
            stats.mediumSolved = activeAccount.getMediumSolved();
            stats.hardSolved = activeAccount.getHardSolved();

            stats.easyTotal = Math.max(activeAccount.getEasySolved(), localEasyTotal);
            stats.mediumTotal = Math.max(activeAccount.getMediumSolved(), localMedTotal);
            stats.hardTotal = Math.max(activeAccount.getHardSolved(), localHardTotal);

            if (activeAccount.getTotalSubmissions() > 0) {
                stats.totalAttempted = Math.max(activeAccount.getTotalSubmissions(), stats.totalSolved);
                stats.acceptanceRate = Math.min(100.0, (stats.totalSolved * 100.0) / stats.totalAttempted);
            } else if (failedAttempts > 0) {
                stats.totalAttempted = stats.totalSolved + failedAttempts;
                stats.acceptanceRate = (stats.totalSolved * 100.0) / stats.totalAttempted;
            } else {
                stats.totalAttempted = stats.totalSolved;
                stats.acceptanceRate = 100.0;
            }
        } else {
            stats.totalSolved = localSolved;
            stats.easySolved = localEasySolved;
            stats.mediumSolved = localMedSolved;
            stats.hardSolved = localHardSolved;
            stats.easyTotal = localEasyTotal;
            stats.mediumTotal = localMedTotal;
            stats.hardTotal = localHardTotal;
            stats.totalAttempted = totalAttempts;
            stats.acceptanceRate = totalAttempts > 0 ? ((acceptedAttempts * 100.0) / totalAttempts) : (localSolved > 0 ? 100.0 : 0.0);
        }

        stats.averageSolvingTimeMinutes = durationCount > 0 ? (durationSum / durationCount) : 0.0;
        stats.weeklyActivity = calculateWeeklyActivity(allAttempts, activeAccount != null ? activeAccount.getSubmissionCalendarJson() : null);

        // Recent 5 problems for this account
        List<ProblemWithDetails> sorted = new ArrayList<>(accountProblems);
        Collections.sort(sorted, (a, b) -> Long.compare(b.getLatestActivityTimestamp(), a.getLatestActivityTimestamp()));
        stats.recentProblems = sorted.subList(0, Math.min(5, sorted.size()));

        // Weakest topics evaluated for active account
        List<TopicDiagnostic> diagnostics = evaluateTopics(accountProblems, activeAccount);
        for (TopicDiagnostic td : diagnostics) {
            if (td.isDataSufficient() && ("Critical".equals(td.getStatusRisk()) || "Needs Improvement".equals(td.getStatusRisk()))) {
                stats.weakestTopics.add(new WeakTopicSummary(
                    td.getTopicName(),
                    td.getTotalAttempts(),
                    td.getFailedCount(),
                    100.0 - td.getAcceptanceRate(),
                    td.getDominantMistake() != null ? td.getDominantMistake().getDisplayName() : "Needs practice"
                ));
            }
        }

        return stats;
    }

    public AnalyticsData computeAnalyticsData(List<ProblemWithDetails> problems, List<CategoryCount> mistakeCounts) {
        return computeAnalyticsData(problems, mistakeCounts, getActiveConnectionSync());
    }

    public AnalyticsData computeAnalyticsData(List<ProblemWithDetails> problems, List<CategoryCount> mistakeCounts, PlatformConnection activeAccount) {
        AnalyticsData data = new AnalyticsData();

        String activeUser = (activeAccount != null && activeAccount.getUsername() != null)
                ? activeAccount.getUsername().trim().toLowerCase() : "";

        List<ProblemWithDetails> accountProblems = filterProblemsForAccount(problems, activeUser);

        int easySolved = 0, medSolved = 0, hardSolved = 0;
        List<CodingAttempt> allAttempts = new ArrayList<>();

        for (ProblemWithDetails p : accountProblems) {
            boolean isSolved = p.isSolved();
            Difficulty diff = Difficulty.fromString(p.problem.getDifficulty());

            if (isSolved) {
                if (diff == Difficulty.EASY) easySolved++;
                else if (diff == Difficulty.MEDIUM) medSolved++;
                else if (diff == Difficulty.HARD) hardSolved++;
            }
            allAttempts.addAll(p.attempts);
        }

        if (activeAccount != null && activeAccount.isConnected() && activeAccount.getTotalSolved() > 0) {
            easySolved = activeAccount.getEasySolved();
            medSolved = activeAccount.getMediumSolved();
            hardSolved = activeAccount.getHardSolved();
        }

        data.difficultyDistribution.put(Difficulty.EASY, easySolved);
        data.difficultyDistribution.put(Difficulty.MEDIUM, medSolved);
        data.difficultyDistribution.put(Difficulty.HARD, hardSolved);

        // Mistake distribution
        if (mistakeCounts != null) {
            for (CategoryCount cc : mistakeCounts) {
                data.mistakeCategoryDistribution.put(cc.getCategory(), cc.getCount());
            }
        }

        // Monthly progress with chronological ordering and unique problem deduplication
        SimpleDateFormat sortFmt = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        SimpleDateFormat displayFmt = new SimpleDateFormat("MMM yyyy", Locale.getDefault());

        class MonthAggregation {
            final String label;
            final Set<Long> uniqueProblemsSolved = new HashSet<>();
            int totalAttempts = 0;
            int acceptedAttempts = 0;
            double totalHours = 0;

            MonthAggregation(String label) { this.label = label; }
        }

        Map<String, MonthAggregation> sortedMonths = new TreeMap<>();
        for (CodingAttempt a : allAttempts) {
            long ts = a.getAttemptedAt();
            if (ts <= 0) continue;
            Date d = new Date(ts);
            String sortKey = sortFmt.format(d);
            MonthAggregation agg = sortedMonths.get(sortKey);
            if (agg == null) {
                agg = new MonthAggregation(displayFmt.format(d));
                sortedMonths.put(sortKey, agg);
            }
            agg.totalAttempts++;
            if (a.isAccepted()) {
                agg.acceptedAttempts++;
                agg.uniqueProblemsSolved.add(a.getProblemId());
            }
            if (a.getDurationMinutes() != null && a.getDurationMinutes() > 0) {
                agg.totalHours += (a.getDurationMinutes() / 60.0);
            }
        }

        // Incorporate real LeetCode calendar submission history
        if (activeAccount != null && activeAccount.getSubmissionCalendarJson() != null) {
            Map<Long, Integer> calSubmissions = parseCalendarJson(activeAccount.getSubmissionCalendarJson());
            for (Map.Entry<Long, Integer> entry : calSubmissions.entrySet()) {
                long ts = entry.getKey();
                int count = entry.getValue();
                Date d = new Date(ts);
                String sortKey = sortFmt.format(d);
                MonthAggregation agg = sortedMonths.get(sortKey);
                if (agg == null) {
                    agg = new MonthAggregation(displayFmt.format(d));
                    sortedMonths.put(sortKey, agg);
                }
                agg.totalAttempts += count;
                int estAc = Math.max(1, (int) Math.round(count * 0.7));
                agg.acceptedAttempts += estAc;
                for (int s = 0; s < estAc; s++) {
                    agg.uniqueProblemsSolved.add(ts + s);
                }
                agg.totalHours += (count * 20.0 / 60.0);
            }
        }

        for (MonthAggregation agg : sortedMonths.values()) {
            int uniqueSolved = agg.uniqueProblemsSolved.size();
            data.monthlyProgress.add(new MonthProgress(agg.label, uniqueSolved, agg.totalAttempts, agg.totalHours));
            data.problemsSolvedOverTime.add(new DatePoint(agg.label, uniqueSolved));
            float rate = agg.totalAttempts > 0 ? ((agg.acceptedAttempts * 100f) / agg.totalAttempts) : 0f;
            data.acceptanceRateTrends.add(new DatePoint(agg.label, rate));
        }

        // Topic performance evaluated for this active account
        List<TopicDiagnostic> diags = evaluateTopics(accountProblems, activeAccount);
        for (TopicDiagnostic td : diags) {
            data.topicPerformance.add(new TopicPerformance(
                td.getTopicName(),
                td.getTotalProblems(),
                td.getSolvedCount(),
                td.getFailedCount(),
                td.getAcceptanceRate()
            ));
        }

        return data;
    }

    public List<GeneratedRecommendation> generateRecommendations(List<ProblemWithDetails> problems) {
        return generateRecommendations(problems, getActiveConnectionSync());
    }

    public List<GeneratedRecommendation> generateRecommendations(List<ProblemWithDetails> problems, PlatformConnection activeAccount) {
        String activeUser = (activeAccount != null && activeAccount.getUsername() != null)
                ? activeAccount.getUsername().trim().toLowerCase() : "";
        List<ProblemWithDetails> accountProblems = filterProblemsForAccount(problems, activeUser);
        int lcTotal = (activeAccount != null && activeAccount.getTotalSolved() > 0) ? activeAccount.getTotalSolved() : 0;
        int lcEasy = (activeAccount != null) ? activeAccount.getEasySolved() : 0;
        int lcMed = (activeAccount != null) ? activeAccount.getMediumSolved() : 0;
        int lcHard = (activeAccount != null) ? activeAccount.getHardSolved() : 0;
        return analysisEngine.generateRecommendations(accountProblems, lcTotal, lcEasy, lcMed, lcHard);
    }

    public List<TopicDiagnostic> evaluateTopics(List<ProblemWithDetails> problems) {
        return evaluateTopics(problems, getActiveConnectionSync());
    }

    public List<TopicDiagnostic> evaluateTopics(List<ProblemWithDetails> problems, PlatformConnection activeAccount) {
        String activeUser = (activeAccount != null && activeAccount.getUsername() != null)
                ? activeAccount.getUsername().trim().toLowerCase() : "";
        List<ProblemWithDetails> accountProblems = filterProblemsForAccount(problems, activeUser);
        Map<String, Integer> tagCounts = (activeAccount != null) ? parseTagCounts(activeAccount.getTagCountsJson()) : Collections.emptyMap();
        if (tagCounts.isEmpty() && preferencesManager != null) {
            tagCounts = preferencesManager.getLeetCodeTagCounts();
        }
        return analysisEngine.evaluateTopics(accountProblems, tagCounts);
    }

    private List<DayActivity> calculateWeeklyActivity(List<CodingAttempt> allAttempts) {
        return calculateWeeklyActivity(allAttempts, null);
    }

    private List<DayActivity> calculateWeeklyActivity(List<CodingAttempt> allAttempts, String calendarJson) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.getDefault());
        List<DayActivity> result = new ArrayList<>();
        Map<Long, Integer> calSubmissions = parseCalendarJson(calendarJson);

        for (int i = 6; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -i);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long start = cal.getTimeInMillis();

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            long end = cal.getTimeInMillis();

            int attempts = 0;
            int solved = 0;
            if (allAttempts != null) {
                for (CodingAttempt a : allAttempts) {
                    if (a.getAttemptedAt() >= start && a.getAttemptedAt() <= end) {
                        attempts++;
                        if (a.isAccepted()) solved++;
                    }
                }
            }

            if (calSubmissions != null && !calSubmissions.isEmpty()) {
                for (Map.Entry<Long, Integer> entry : calSubmissions.entrySet()) {
                    long ts = entry.getKey();
                    if (ts >= start && ts <= end) {
                        int c = entry.getValue();
                        attempts += c;
                        solved += Math.min(c, Math.max(1, (int) Math.round(c * 0.7)));
                    }
                }
            }

            result.add(new DayActivity(
                dayFormat.format(new Date(start)),
                dateFormat.format(new Date(start)),
                solved,
                attempts
            ));
        }
        return result;
    }

    private Map<Long, Integer> parseCalendarJson(String json) {
        Map<Long, Integer> result = new HashMap<>();
        if (json == null || json.trim().isEmpty() || json.equals("{}")) return result;
        try {
            org.json.JSONObject obj = new org.json.JSONObject(json);
            java.util.Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String k = keys.next();
                try {
                    long epochSec = Long.parseLong(k);
                    int count = obj.getInt(k);
                    result.put(epochSec * 1000L, count);
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
        return result;
    }
}
