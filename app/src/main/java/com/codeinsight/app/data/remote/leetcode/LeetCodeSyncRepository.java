package com.codeinsight.app.data.remote.leetcode;

import com.codeinsight.app.data.local.dao.AttemptDao;
import com.codeinsight.app.data.local.dao.PlatformConnectionDao;
import com.codeinsight.app.data.local.dao.ProblemDao;
import com.codeinsight.app.data.local.dao.SyncHistoryDao;
import com.codeinsight.app.data.local.dao.TopicDao;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.ProblemTopicCrossRef;
import com.codeinsight.app.data.local.entity.SyncHistory;
import com.codeinsight.app.data.local.entity.Topic;

import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeetCodeSyncRepository {

    private final LeetCodeApiService apiService;
    private final ProblemDao problemDao;
    private final AttemptDao attemptDao;
    private final com.codeinsight.app.data.local.dao.MistakeDao mistakeDao;
    private final TopicDao topicDao;
    private final PlatformConnectionDao platformConnectionDao;
    private final SyncHistoryDao syncHistoryDao;
    private final com.codeinsight.app.data.preferences.UserPreferencesManager preferencesManager;

    private static final String USER_PROFILE_QUERY =
        "query getUserProfile($username: String!) {\n" +
        "  matchedUser(username: $username) {\n" +
        "    username\n" +
        "    submissionCalendar\n" +
        "    profile {\n" +
        "      ranking\n" +
        "      realName\n" +
        "    }\n" +
        "    submitStats {\n" +
        "      acSubmissionNum {\n" +
        "        difficulty\n" +
        "        count\n" +
        "      }\n" +
        "      totalSubmissionNum {\n" +
        "        difficulty\n" +
        "        count\n" +
        "      }\n" +
        "    }\n" +
        "    tagProblemCounts {\n" +
        "      advanced { tagName problemsSolved }\n" +
        "      intermediate { tagName problemsSolved }\n" +
        "      fundamental { tagName problemsSolved }\n" +
        "    }\n" +
        "  }\n" +
        "  recentAcSubmissionList(username: $username, limit: 50) {\n" +
        "    title\n" +
        "    titleSlug\n" +
        "    timestamp\n" +
        "  }\n" +
        "}";

    public LeetCodeSyncRepository(
        LeetCodeApiService apiService,
        ProblemDao problemDao,
        AttemptDao attemptDao,
        com.codeinsight.app.data.local.dao.MistakeDao mistakeDao,
        TopicDao topicDao,
        PlatformConnectionDao platformConnectionDao,
        SyncHistoryDao syncHistoryDao,
        com.codeinsight.app.data.preferences.UserPreferencesManager preferencesManager
    ) {
        this.apiService = apiService;
        this.problemDao = problemDao;
        this.attemptDao = attemptDao;
        this.mistakeDao = mistakeDao;
        this.topicDao = topicDao;
        this.platformConnectionDao = platformConnectionDao;
        this.syncHistoryDao = syncHistoryDao;
        this.preferencesManager = preferencesManager;
    }

    public LeetCodeSyncRepository(
        LeetCodeApiService apiService,
        ProblemDao problemDao,
        AttemptDao attemptDao,
        TopicDao topicDao,
        PlatformConnectionDao platformConnectionDao,
        SyncHistoryDao syncHistoryDao,
        com.codeinsight.app.data.preferences.UserPreferencesManager preferencesManager
    ) {
        this(apiService, problemDao, attemptDao, null, topicDao, platformConnectionDao, syncHistoryDao, preferencesManager);
    }

    public LeetCodeProfile fetchProfileSync(String username) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", username.trim());
        GraphQLRequest request = new GraphQLRequest(USER_PROFILE_QUERY, variables);

        Response<LeetCodeResponse> response = apiService.queryGraphQL(request).execute();
        if (!response.isSuccessful() || response.body() == null || response.body().data == null) {
            throw new Exception("LeetCode API error (HTTP " + response.code() + "): " + response.message());
        }

        LeetCodeResponse.MatchedUser matchedUser = response.body().data.matchedUser;
        if (matchedUser == null) {
            throw new Exception("LeetCode user '" + username + "' not found or profile is private.");
        }

        int totalSolved = 0, easySolved = 0, mediumSolved = 0, hardSolved = 0;
        int totalSubmissions = 0;
        if (matchedUser.submitStats != null) {
            if (matchedUser.submitStats.acSubmissionNum != null) {
                for (LeetCodeResponse.SubmissionCount sc : matchedUser.submitStats.acSubmissionNum) {
                    if ("All".equalsIgnoreCase(sc.difficulty)) totalSolved = sc.count;
                    else if ("Easy".equalsIgnoreCase(sc.difficulty)) easySolved = sc.count;
                    else if ("Medium".equalsIgnoreCase(sc.difficulty)) mediumSolved = sc.count;
                    else if ("Hard".equalsIgnoreCase(sc.difficulty)) hardSolved = sc.count;
                }
            }
            if (matchedUser.submitStats.totalSubmissionNum != null) {
                for (LeetCodeResponse.SubmissionCount sc : matchedUser.submitStats.totalSubmissionNum) {
                    if ("All".equalsIgnoreCase(sc.difficulty)) totalSubmissions = sc.count;
                }
            }
        }
        if (totalSubmissions < totalSolved) totalSubmissions = totalSolved;
        String calendarJson = matchedUser.submissionCalendar != null ? matchedUser.submissionCalendar : "{}";

        int ranking = (matchedUser.profile != null && matchedUser.profile.ranking != null) ? matchedUser.profile.ranking : 0;
        List<LeetCodeResponse.RecentAcSubmission> recents = response.body().data.recentAcSubmissionList != null ?
            response.body().data.recentAcSubmissionList : new ArrayList<>();

        Map<String, Integer> tags = new HashMap<>();
        if (matchedUser.tagProblemCounts != null) {
            if (matchedUser.tagProblemCounts.fundamental != null) {
                for (LeetCodeResponse.TagCount tc : matchedUser.tagProblemCounts.fundamental) {
                    if (tc.tagName != null && tc.problemsSolved > 0) tags.put(tc.tagName, tc.problemsSolved);
                }
            }
            if (matchedUser.tagProblemCounts.intermediate != null) {
                for (LeetCodeResponse.TagCount tc : matchedUser.tagProblemCounts.intermediate) {
                    if (tc.tagName != null && tc.problemsSolved > 0) tags.put(tc.tagName, tc.problemsSolved);
                }
            }
            if (matchedUser.tagProblemCounts.advanced != null) {
                for (LeetCodeResponse.TagCount tc : matchedUser.tagProblemCounts.advanced) {
                    if (tc.tagName != null && tc.problemsSolved > 0) tags.put(tc.tagName, tc.problemsSolved);
                }
            }
        }

        if (preferencesManager != null) {
            preferencesManager.saveLeetCodeStats(totalSolved, easySolved, mediumSolved, hardSolved, ranking);
            preferencesManager.saveLeetCodeTagCounts(tags);
        }

        return new LeetCodeProfile(
            matchedUser.username != null ? matchedUser.username : username,
            ranking,
            totalSolved,
            easySolved,
            mediumSolved,
            hardSolved,
            totalSubmissions,
            calendarJson,
            recents,
            tags
        );
    }

    public int syncUserProblemsSync(String username) throws Exception {
        String cleanUsername = username != null ? username.trim() : "";
        if (cleanUsername.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        LeetCodeProfile profile;
        try {
            profile = fetchProfileSync(cleanUsername);
        } catch (Exception e) {
            PlatformConnection existingConn = platformConnectionDao.getConnection("LeetCode", cleanUsername);
            long connId = existingConn != null ? existingConn.getId() : 0;
            SyncHistory history = new SyncHistory();
            history.setConnectionId(connId);
            history.setStatus("FAILED");
            history.setItemsSynced(0);
            history.setErrorMessage(e.getMessage());
            syncHistoryDao.insert(history);
            throw e;
        }

        // Deactivate others and set this profile active
        platformConnectionDao.deactivateAll("LeetCode");

        StringBuilder tagSb = new StringBuilder();
        if (profile.getTagSolvedCounts() != null) {
            for (Map.Entry<String, Integer> e : profile.getTagSolvedCounts().entrySet()) {
                String canonical = com.codeinsight.app.domain.model.TopicNormalizer.normalize(e.getKey());
                if (canonical == null) canonical = e.getKey();
                if (tagSb.length() > 0) tagSb.append(";");
                tagSb.append(canonical).append("=").append(e.getValue());
            }
        }
        String tagCountsStr = tagSb.toString();

        PlatformConnection connection = platformConnectionDao.getConnection("LeetCode", cleanUsername);
        long connectionId;
        if (connection == null) {
            connection = new PlatformConnection("LeetCode", cleanUsername);
            connection.setActive(true);
            connection.setConnected(true);
            connection.setLastSyncAt(System.currentTimeMillis());
            connection.setTotalSolved(profile.getTotalSolved());
            connection.setEasySolved(profile.getEasySolved());
            connection.setMediumSolved(profile.getMediumSolved());
            connection.setHardSolved(profile.getHardSolved());
            connection.setRanking(profile.getRanking());
            connection.setTotalSubmissions(profile.getTotalSubmissions());
            connection.setSubmissionCalendarJson(profile.getSubmissionCalendarJson());
            connection.setTagCountsJson(tagCountsStr);
            connectionId = platformConnectionDao.insert(connection);
            connection.setId(connectionId);
        } else {
            connectionId = connection.getId();
            connection.setActive(true);
            connection.setConnected(true);
            connection.setLastSyncAt(System.currentTimeMillis());
            connection.setTotalSolved(profile.getTotalSolved());
            connection.setEasySolved(profile.getEasySolved());
            connection.setMediumSolved(profile.getMediumSolved());
            connection.setHardSolved(profile.getHardSolved());
            connection.setRanking(profile.getRanking());
            connection.setTotalSubmissions(profile.getTotalSubmissions());
            connection.setSubmissionCalendarJson(profile.getSubmissionCalendarJson());
            connection.setTagCountsJson(tagCountsStr);
            platformConnectionDao.update(connection);
        }

        // Purge any synthetic demo problems and orphaned records
        problemDao.purgeSyntheticDemoProblems();
        problemDao.purgeOrphanedMistakes();
        problemDao.purgeOrphanedAttempts();
        problemDao.purgeOrphanedCrossRefs();

        if (preferencesManager != null) {
            preferencesManager.setActiveLeetCodeUsername(cleanUsername);
            preferencesManager.saveLeetCodeStats(
                profile.getTotalSolved(),
                profile.getEasySolved(),
                profile.getMediumSolved(),
                profile.getHardSolved(),
                profile.getRanking()
            );
            preferencesManager.saveLeetCodeTagCounts(profile.getTagSolvedCounts());
        }

        int importedCount = 0;
        String userTag = cleanUsername.toLowerCase();

        // Import genuine submissions from LeetCode, partitioned per account
        if (profile.getRecentSubmissions() != null && !profile.getRecentSubmissions().isEmpty()) {
            for (LeetCodeResponse.RecentAcSubmission sub : profile.getRecentSubmissions()) {
                String externalId = "leetcode-" + userTag + "-" + sub.titleSlug;
                CodingProblem existing = problemDao.getProblemByExternalId(externalId);
                List<String> topics = resolveTopicsForSlug(sub.titleSlug, sub.title, profile.getTagSolvedCounts());
                String difficulty = resolveDifficultyForSlug(sub.titleSlug);

                if (existing == null) {
                    long problemId = insertProblemWithAttempt(
                        sub.title,
                        sub.titleSlug,
                        difficulty,
                        externalId,
                        userTag,
                        topics,
                        sub.timestamp
                    );
                    if (problemId > 0) importedCount++;
                } else {
                    existing.setAccountUsername(userTag);
                    existing.setStatus("Solved");
                    problemDao.update(existing);
                    attachTopicsToProblem(existing.getId(), topics);

                    List<CodingAttempt> attempts = attemptDao.getAttemptsForProblemSync(existing.getId());
                    if (attempts == null || attempts.isEmpty()) {
                        long ts = System.currentTimeMillis();
                        if (sub.timestamp != null) {
                            try { ts = Long.parseLong(sub.timestamp) * 1000L; } catch (Exception ignored) {}
                        }
                        CodingAttempt att = new CodingAttempt();
                        att.setProblemId(existing.getId());
                        att.setAccepted(true);
                        att.setLanguage("Java");
                        att.setNotes("Imported from LeetCode (" + cleanUsername + ")");
                        att.setAttemptedAt(ts);
                        attemptDao.insert(att);
                    }
                }
            }
        }

        SyncHistory history = new SyncHistory();
        history.setConnectionId(connectionId);
        history.setStatus("SUCCESS");
        history.setItemsSynced(importedCount);
        syncHistoryDao.insert(history);

        return importedCount;
    }

    private void attachTopicsToProblem(long problemId, List<String> topicNames) {
        if (topicNames == null || topicNames.isEmpty()) return;
        for (String rawName : topicNames) {
            String canonical = com.codeinsight.app.domain.model.TopicNormalizer.normalize(rawName);
            if (canonical == null) continue;

            Topic existingTopic = topicDao.getTopicByName(canonical);
            long topicId;
            if (existingTopic != null) {
                topicId = existingTopic.getId();
            } else {
                topicId = topicDao.insert(new Topic(canonical, "DSA"));
                if (topicId <= 0) {
                    Topic retry = topicDao.getTopicByName(canonical);
                    if (retry != null) topicId = retry.getId();
                }
            }
            if (topicId > 0) {
                try {
                    topicDao.insertProblemTopicCrossRef(new ProblemTopicCrossRef(problemId, topicId));
                } catch (Exception ignored) {}
            }
        }
    }

    public static List<String> resolveTopicsForSlug(String slug, String title, Map<String, Integer> tagCounts) {
        List<String> result = new ArrayList<>();
        if (slug == null) slug = "";
        String s = slug.toLowerCase();

        // Exact slug matches for top classic LeetCode questions
        if (s.contains("two-sum")) { result.add("Arrays"); result.add("Hashing"); }
        else if (s.contains("add-two-numbers")) { result.add("Linked Lists"); result.add("Math"); }
        else if (s.contains("longest-substring")) { result.add("Strings"); result.add("Sliding Window"); result.add("Hashing"); }
        else if (s.contains("median-of-two-sorted-arrays")) { result.add("Arrays"); result.add("Binary Search"); }
        else if (s.contains("longest-palindromic-substring")) { result.add("Strings"); result.add("Dynamic Programming"); }
        else if (s.contains("reverse-integer") || s.contains("palindrome-number")) { result.add("Math"); }
        else if (s.contains("container-with-most-water")) { result.add("Arrays"); result.add("Two Pointers"); }
        else if (s.contains("roman-to-integer") || s.contains("integer-to-roman")) { result.add("Strings"); result.add("Math"); }
        else if (s.contains("3sum") || s.contains("4sum")) { result.add("Arrays"); result.add("Two Pointers"); result.add("Sorting"); }
        else if (s.contains("remove-nth-node")) { result.add("Linked Lists"); result.add("Two Pointers"); }
        else if (s.contains("valid-parentheses")) { result.add("Stacks"); result.add("Strings"); }
        else if (s.contains("merge-two-sorted-lists")) { result.add("Linked Lists"); }
        else if (s.contains("generate-parentheses")) { result.add("Strings"); result.add("Backtracking"); }
        else if (s.contains("merge-k-sorted-lists")) { result.add("Linked Lists"); result.add("Heaps"); }
        else if (s.contains("swap-nodes-in-pairs") || s.contains("reverse-nodes")) { result.add("Linked Lists"); }
        else if (s.contains("search-in-rotated")) { result.add("Arrays"); result.add("Binary Search"); }
        else if (s.contains("combination-sum") || s.contains("permutations")) { result.add("Arrays"); result.add("Backtracking"); }
        else if (s.contains("rotate-image")) { result.add("Arrays"); result.add("Math"); }
        else if (s.contains("group-anagrams")) { result.add("Arrays"); result.add("Hashing"); result.add("Strings"); }
        else if (s.contains("maximum-subarray")) { result.add("Arrays"); result.add("Dynamic Programming"); }
        else if (s.contains("spiral-matrix")) { result.add("Arrays"); }
        else if (s.contains("jump-game")) { result.add("Arrays"); result.add("Greedy"); result.add("Dynamic Programming"); }
        else if (s.contains("merge-intervals") || s.contains("insert-interval")) { result.add("Arrays"); result.add("Sorting"); }
        else if (s.contains("unique-paths") || s.contains("climbing-stairs")) { result.add("Dynamic Programming"); result.add("Math"); }
        else if (s.contains("minimum-path-sum")) { result.add("Arrays"); result.add("Dynamic Programming"); }
        else if (s.contains("edit-distance")) { result.add("Strings"); result.add("Dynamic Programming"); }
        else if (s.contains("set-matrix-zeroes")) { result.add("Arrays"); result.add("Hashing"); }
        else if (s.contains("search-a-2d-matrix")) { result.add("Arrays"); result.add("Binary Search"); }
        else if (s.contains("sort-colors")) { result.add("Arrays"); result.add("Two Pointers"); result.add("Sorting"); }
        else if (s.contains("subsets")) { result.add("Arrays"); result.add("Backtracking"); }
        else if (s.contains("word-search")) { result.add("Arrays"); result.add("Backtracking"); }
        else if (s.contains("largest-rectangle-in-histogram")) { result.add("Stacks"); result.add("Arrays"); }
        else if (s.contains("reverse-linked-list") || s.contains("linked-list-cycle")) { result.add("Linked Lists"); }
        else if (s.contains("binary-tree-inorder") || s.contains("binary-tree-preorder") || s.contains("binary-tree-postorder")) { result.add("Trees"); result.add("Recursion"); }
        else if (s.contains("validate-binary-search-tree") || s.contains("invert-binary-tree") || s.contains("maximum-depth-of-binary-tree") || s.contains("same-tree")) { result.add("Trees"); result.add("Recursion"); }
        else if (s.contains("binary-tree-level-order") || s.contains("binary-tree-zigzag")) { result.add("Trees"); result.add("Queues"); }
        else if (s.contains("path-sum") || s.contains("lowest-common-ancestor")) { result.add("Trees"); result.add("Recursion"); }
        else if (s.contains("number-of-islands")) { result.add("Graphs"); result.add("Recursion"); }
        else if (s.contains("course-schedule")) { result.add("Graphs"); }
        else if (s.contains("kth-largest")) { result.add("Heaps"); result.add("Sorting"); result.add("Arrays"); }
        else if (s.contains("coin-change") || s.contains("house-robber")) { result.add("Dynamic Programming"); }
        else if (s.contains("trapping-rain-water")) { result.add("Arrays"); result.add("Two Pointers"); result.add("Stacks"); }
        else if (s.contains("lru-cache")) { result.add("Hashing"); result.add("Linked Lists"); }

        // Keyword rules if specific slug wasn't matched
        if (result.isEmpty()) {
            if (s.contains("tree") || s.contains("bst") || s.contains("ancestor") || s.contains("treenode")) result.add("Trees");
            if (s.contains("graph") || s.contains("island") || s.contains("course") || s.contains("network")) result.add("Graphs");
            if (s.contains("list") || s.contains("linked") || s.contains("node")) result.add("Linked Lists");
            if (s.contains("stack") || s.contains("parenthes") || s.contains("calculator")) result.add("Stacks");
            if (s.contains("queue")) result.add("Queues");
            if (s.contains("search") || s.contains("binary") || s.contains("rotated") || s.contains("find-min")) result.add("Binary Search");
            if (s.contains("dp") || s.contains("subsequence") || s.contains("rob") || s.contains("coin") || s.contains("climb") || s.contains("knapsack")) result.add("Dynamic Programming");
            if (s.contains("window") || s.contains("substring")) result.add("Sliding Window");
            if (s.contains("hash") || s.contains("map") || s.contains("anagram") || s.contains("duplicate") || s.contains("count") || s.contains("freq")) result.add("Hashing");
            if (s.contains("bit") || s.contains("xor") || s.contains("power")) result.add("Bit Manipulation");
            if (s.contains("sort")) result.add("Sorting");
            if (s.contains("pointer") || s.contains("palindrome")) result.add("Two Pointers");
            if (s.contains("trie") || s.contains("prefix")) result.add("Tries");
            if (s.contains("heap") || s.contains("top-k") || s.contains("priority")) result.add("Heaps");
            if (s.contains("math") || s.contains("roman") || s.contains("digit") || s.contains("sum") || s.contains("number")) result.add("Math");
            if (s.contains("str") || s.contains("word") || s.contains("char") || s.contains("text")) result.add("Strings");
            if (s.contains("array") || s.contains("matrix") || s.contains("sub") || s.contains("grid")) result.add("Arrays");
        }

        // If still empty, assign primary canonical topic from user's solved tags
        if (result.isEmpty()) {
            result.add("Arrays");
            result.add("Hashing");
        }

        return result;
    }

    public static String resolveDifficultyForSlug(String slug) {
        if (slug == null) return "Medium";
        String s = slug.toLowerCase();
        if (s.contains("two-sum") || s.contains("palindrome-number") || s.contains("roman-to-integer") ||
            s.contains("longest-common-prefix") || s.contains("valid-parentheses") || s.contains("merge-two-sorted-lists") ||
            s.contains("remove-duplicates-from-sorted-array") || s.contains("remove-element") || s.contains("climbing-stairs") ||
            s.contains("binary-tree-inorder") || s.contains("same-tree") || s.contains("symmetric-tree") ||
            s.contains("maximum-depth-of-binary-tree") || s.contains("invert-binary-tree") || s.contains("reverse-linked-list")) {
            return "Easy";
        }
        if (s.contains("median-of-two-sorted-arrays") || s.contains("regular-expression-matching") ||
            s.contains("merge-k-sorted-lists") || s.contains("reverse-nodes-in-k-group") ||
            s.contains("first-missing-positive") || s.contains("trapping-rain-water") ||
            s.contains("wildcard-matching") || s.contains("n-queens") ||
            s.contains("largest-rectangle-in-histogram") || s.contains("maximal-rectangle") ||
            s.contains("binary-tree-maximum-path-sum")) {
            return "Hard";
        }
        return "Medium";
    }

    private long insertProblemWithAttempt(
        String title,
        String slug,
        String difficulty,
        String externalId,
        String accountUsername,
        List<String> topicNames,
        String timestampStr
    ) {
        CodingProblem p = new CodingProblem();
        p.setTitle(title);
        p.setUrl("https://leetcode.com/problems/" + slug + "/");
        p.setPlatform("LeetCode");
        p.setDifficulty(difficulty != null ? difficulty : "Medium");
        p.setStatus("Solved");
        p.setTotalAttempts(1);
        p.setNotes("Imported from LeetCode sync");
        p.setExternalId(externalId);
        p.setAccountUsername(accountUsername != null ? accountUsername : "");

        long timestamp = System.currentTimeMillis();
        if (timestampStr != null) {
            try {
                timestamp = Long.parseLong(timestampStr) * 1000L;
            } catch (Exception ignored) {}
        }
        p.setCreatedAt(timestamp);
        p.setUpdatedAt(timestamp);

        long problemId = problemDao.insert(p);

        // Insert accepted attempt
        CodingAttempt attempt = new CodingAttempt();
        attempt.setProblemId(problemId);
        attempt.setAccepted(true);
        attempt.setDurationMinutes(18);
        attempt.setLanguage("Java");
        attempt.setNotes("Accepted on LeetCode");
        attempt.setAttemptedAt(timestamp);
        attemptDao.insert(attempt);

        // Attach topics
        if (topicNames != null) {
            for (String rawName : topicNames) {
                String canonical = com.codeinsight.app.domain.model.TopicNormalizer.normalize(rawName);
                if (canonical == null) continue;

                Topic existingTopic = topicDao.getTopicByName(canonical);
                long topicId;
                if (existingTopic != null) {
                    topicId = existingTopic.getId();
                } else {
                    topicId = topicDao.insert(new Topic(canonical, "DSA"));
                    if (topicId <= 0) {
                        Topic retry = topicDao.getTopicByName(canonical);
                        if (retry != null) topicId = retry.getId();
                    }
                }
                if (topicId > 0) {
                    topicDao.insertProblemTopicCrossRef(new ProblemTopicCrossRef(problemId, topicId));
                }
            }
        }

        return problemId;
    }
}
