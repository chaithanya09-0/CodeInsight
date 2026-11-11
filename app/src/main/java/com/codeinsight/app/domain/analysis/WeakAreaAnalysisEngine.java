package com.codeinsight.app.domain.analysis;

import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.domain.model.MistakeCategory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeakAreaAnalysisEngine {

    public static final int MIN_ATTEMPTS_FOR_TOPIC_ANALYSIS = 5;
    public static final int MIN_TOTAL_PROBLEMS_FOR_GLOBAL_INSIGHTS = 5;

    public List<TopicDiagnostic> evaluateTopics(List<ProblemWithDetails> problems) {
        return evaluateTopics(problems, Collections.emptyMap());
    }

    public List<TopicDiagnostic> evaluateTopics(List<ProblemWithDetails> problems, Map<String, Integer> platformTagCounts) {
        Map<String, List<ProblemWithDetails>> topicMap = new HashMap<>();
        if (problems != null) {
            for (ProblemWithDetails p : problems) {
                for (Topic t : p.topics) {
                    if (!topicMap.containsKey(t.getName())) {
                        topicMap.put(t.getName(), new ArrayList<>());
                    }
                    topicMap.get(t.getName()).add(p);
                }
            }
        }

        List<TopicDiagnostic> diagnostics = new ArrayList<>();
        for (Map.Entry<String, List<ProblemWithDetails>> entry : topicMap.entrySet()) {
            String topicName = entry.getKey();
            List<ProblemWithDetails> pList = entry.getValue();

            int totalAttempts = 0;
            int failedCount = 0;
            int solvedCount = 0;

            for (ProblemWithDetails pwd : pList) {
                if (pwd.isSolved()) {
                    solvedCount++;
                }
                for (CodingAttempt attempt : pwd.attempts) {
                    totalAttempts++;
                    if (!attempt.isAccepted()) {
                        failedCount++;
                    }
                }
            }

            int platformSolved = 0;
            if (platformTagCounts != null) {
                for (Map.Entry<String, Integer> pEntry : platformTagCounts.entrySet()) {
                    if (pEntry.getKey().equalsIgnoreCase(topicName)) {
                        platformSolved = pEntry.getValue();
                        break;
                    }
                }
            }
            solvedCount = Math.max(solvedCount, platformSolved);
            totalAttempts = Math.max(totalAttempts, solvedCount + failedCount);

            boolean isSufficient = totalAttempts >= MIN_ATTEMPTS_FOR_TOPIC_ANALYSIS;
            double acceptanceRate = totalAttempts > 0 ?
                (((totalAttempts - failedCount) * 100.0) / totalAttempts) : (solvedCount > 0 ? 100.0 : 0.0);

            // Find top mistake in this topic
            Map<MistakeCategory, Integer> mistakeFreq = new HashMap<>();
            for (ProblemWithDetails pwd : pList) {
                for (Mistake m : pwd.mistakes) {
                    MistakeCategory cat = MistakeCategory.fromString(m.getCategory());
                    mistakeFreq.put(cat, mistakeFreq.getOrDefault(cat, 0) + 1);
                }
            }

            MistakeCategory topMistake = null;
            int topCount = 0;
            for (Map.Entry<MistakeCategory, Integer> mEntry : mistakeFreq.entrySet()) {
                if (mEntry.getValue() > topCount) {
                    topCount = mEntry.getValue();
                    topMistake = mEntry.getKey();
                }
            }

            String statusRisk;
            String rationale;
            if (!isSufficient) {
                statusRisk = "Insufficient Data";
                rationale = "Only " + totalAttempts + " attempt(s) recorded. At least 5 attempts required for reliable assessment.";
            } else if (acceptanceRate < 40.0) {
                statusRisk = "Critical";
                rationale = failedCount + " out of " + totalAttempts + " attempts failed (" + (int)Math.round(acceptanceRate) + "% acceptance)."
                    + (topMistake != null ? " Recurring issue: " + topMistake.getDisplayName() + " (" + topCount + "x)." : "");
            } else if (acceptanceRate < 65.0) {
                statusRisk = "Needs Improvement";
                rationale = "Acceptance rate (" + (int)Math.round(acceptanceRate) + "%) is below standard across " + totalAttempts + " attempts (" + failedCount + " failed)."
                    + (topMistake != null ? " Recurring issue: " + topMistake.getDisplayName() + " (" + topCount + "x)." : "");
            } else {
                statusRisk = "Strong";
                rationale = "Consistent performance with " + (int)Math.round(acceptanceRate) + "% acceptance across " + totalAttempts + " attempts (" + solvedCount + " solved).";
            }

            diagnostics.add(new TopicDiagnostic(
                topicName,
                Math.max(pList.size(), solvedCount),
                totalAttempts,
                solvedCount,
                failedCount,
                acceptanceRate,
                isSufficient,
                topMistake,
                topCount,
                statusRisk,
                rationale
            ));
        }

        // Add platform verified topics if not already in local database
        if (platformTagCounts != null) {
            for (Map.Entry<String, Integer> pEntry : platformTagCounts.entrySet()) {
                String tName = pEntry.getKey();
                int count = pEntry.getValue();
                boolean existsInLocal = false;
                for (String localKey : topicMap.keySet()) {
                    if (localKey.equalsIgnoreCase(tName)) {
                        existsInLocal = true;
                        break;
                    }
                }
                if (!existsInLocal) {
                    String statusRisk;
                    String rationale;
                    boolean platformSufficient = count >= MIN_ATTEMPTS_FOR_TOPIC_ANALYSIS;
                    if (!platformSufficient) {
                        statusRisk = "Insufficient Data";
                        rationale = "Only " + count + " verified problem(s) on record. At least 5 attempts required for assessment.";
                    } else {
                        // User has >= 5 solved problems on platform with 100% acceptance!
                        statusRisk = "Strong";
                        rationale = "Consistent track record with " + count + " verified solved problems on platform.";
                    }

                    diagnostics.add(new TopicDiagnostic(
                        tName,
                        count,
                        count,
                        count,
                        0,
                        100.0,
                        platformSufficient,
                        null,
                        0,
                        statusRisk,
                        rationale
                    ));
                }
            }
        }

        Collections.sort(diagnostics, (a, b) -> {
            int scoreA = getRiskRank(a.getStatusRisk());
            int scoreB = getRiskRank(b.getStatusRisk());
            if (scoreA != scoreB) return Integer.compare(scoreA, scoreB);
            return Double.compare(a.getAcceptanceRate(), b.getAcceptanceRate());
        });

        return diagnostics;
    }

    private int getRiskRank(String risk) {
        switch (risk) {
            case "Critical": return 1;
            case "Needs Improvement": return 2;
            case "Strong": return 3;
            case "Insufficient Data": return 4;
            default: return 5;
        }
    }

    public List<GeneratedRecommendation> generateRecommendations(List<ProblemWithDetails> problems) {
        return generateRecommendations(problems, 0, 0, 0, 0);
    }

    public List<GeneratedRecommendation> generateRecommendations(
        List<ProblemWithDetails> problems,
        int lcTotal,
        int lcEasy,
        int lcMed,
        int lcHard
    ) {
        int totalAttempts = 0;
        int totalMistakes = 0;
        if (problems != null) {
            for (ProblemWithDetails p : problems) {
                totalAttempts += p.attempts.size();
                totalMistakes += p.mistakes.size();
            }
        }

        if (totalAttempts < MIN_TOTAL_PROBLEMS_FOR_GLOBAL_INSIGHTS && lcTotal == 0) {
            return new ArrayList<>();
        }

        List<GeneratedRecommendation> recommendations = new ArrayList<>();
        List<TopicDiagnostic> diagnostics = evaluateTopics(problems != null ? problems : new ArrayList<>());

        // Rule 1: Critical Weak Topic (Acceptance rate < 40% with data sufficiency)
        for (TopicDiagnostic diag : diagnostics) {
            if (diag.isDataSufficient() && diag.getAcceptanceRate() < 40.0) {
                String mistakeNote = diag.getDominantMistake() != null ?
                    " Frequently encountered issue: \"" + diag.getDominantMistake().getDisplayName() + "\" (" + diag.getDominantMistakeCount() + " times)." : "";

                recommendations.add(new GeneratedRecommendation(
                    diag.getTopicName(),
                    "WEAK_TOPIC",
                    "Strengthen Fundamentals in " + diag.getTopicName(),
                    diag.getTopicName() + " is currently one of your weakest topics with an acceptance rate of " + (int)diag.getAcceptanceRate() + "%. Practice basic problems to build core invariants before moving to complex challenges." + mistakeNote,
                    "Based on " + diag.getTotalAttempts() + " recorded attempts with " + diag.getFailedCount() + " failures (" + (int)(100 - diag.getAcceptanceRate()) + "% failure rate) across " + diag.getTotalProblems() + " problems.",
                    Arrays.asList(
                        "Solve 3-5 Easy problems tagged with " + diag.getTopicName(),
                        "Write out step-by-step logic and invariants before coding",
                        "Trace through manual examples with boundary values"
                    ),
                    3
                ));
            }
        }

        // Rule 2: Repeated Mistake Pattern
        if (problems != null) {
            Map<MistakeCategory, List<Mistake>> mistakeMap = new HashMap<>();
            for (ProblemWithDetails p : problems) {
                for (Mistake m : p.mistakes) {
                    MistakeCategory cat = MistakeCategory.fromString(m.getCategory());
                    if (!mistakeMap.containsKey(cat)) {
                        mistakeMap.put(cat, new ArrayList<>());
                    }
                    mistakeMap.get(cat).add(m);
                }
            }

            for (Map.Entry<MistakeCategory, List<Mistake>> entry : mistakeMap.entrySet()) {
                MistakeCategory category = entry.getKey();
                List<Mistake> list = entry.getValue();

                if (list.size() >= 2) {
                    recommendations.add(new GeneratedRecommendation(
                        category.getDisplayName(),
                        "MISTAKE_PATTERN",
                        "Recurring Issue: " + category.getDisplayName(),
                        "You have recorded \"" + category.getDisplayName() + "\" " + list.size() + " times across your attempts. " + getAdviceForMistake(category),
                        "Identified " + list.size() + " occurrences of " + category.getDisplayName() + " in recent problem logs.",
                        getChecklistForCategory(category),
                        list.size() >= 3 ? 3 : 2
                    ));
                }
            }
        }

        // Rule 3: Prerequisite Practice (Recursion & Backtracking)
        TopicDiagnostic recursionDiag = null;
        TopicDiagnostic backtrackingDiag = null;
        for (TopicDiagnostic td : diagnostics) {
            if ("Recursion".equalsIgnoreCase(td.getTopicName())) recursionDiag = td;
            if ("Backtracking".equalsIgnoreCase(td.getTopicName())) backtrackingDiag = td;
        }

        if (recursionDiag != null && recursionDiag.getAcceptanceRate() < 50.0 && backtrackingDiag != null && backtrackingDiag.getTotalAttempts() > 0) {
            recommendations.add(new GeneratedRecommendation(
                "Recursion & Backtracking",
                "PREREQUISITE_PRACTICE",
                "Master Recursion Before Advanced Backtracking",
                "Backtracking builds directly upon clean recursive state management. Your recursion acceptance rate is " + (int)recursionDiag.getAcceptanceRate() + "%. Practicing basic recursion will directly elevate your backtracking success.",
                "Recursion has " + recursionDiag.getFailedCount() + " failures out of " + recursionDiag.getTotalAttempts() + " attempts while attempting Backtracking problems.",
                Arrays.asList(
                    "Practice identifying base cases first",
                    "Draw recursion trees on paper for small inputs (n=2, n=3)",
                    "Verify state restoration (backtracking step) is cleanly isolated"
                ),
                2
            ));
        }

        // Rule 4: Difficulty Progression (LeetCode or Local Portfolio)
        if (lcEasy >= 10 || (lcTotal > 0 && lcEasy > lcMed * 2)) {
            recommendations.add(new GeneratedRecommendation(
                "Difficulty Progression",
                "LEVEL_UP",
                "Level Up: Transition from Easy to Medium Challenges",
                "Your LeetCode portfolio shows solid foundational practice with " + lcEasy + " Easy problems solved. However, product-based company screening rounds predominantly test Medium-tier questions (current: " + lcMed + " Medium, " + lcHard + " Hard). Elevating your focus to Medium problems will rapidly build algorithmic confidence.",
                "LeetCode distribution: " + lcEasy + " Easy (" + (lcTotal > 0 ? (lcEasy * 100 / lcTotal) : 0) + "%), " + lcMed + " Medium, " + lcHard + " Hard.",
                Arrays.asList(
                    "Filter LeetCode problems by 'Medium' difficulty in Arrays & Two Pointers",
                    "Attempt each Medium problem for 25 minutes without viewing the editorial",
                    "Analyze time complexity upfront: target O(N) or O(N log N) rather than brute-force O(N^2)"
                ),
                3
            ));
        }

        // Rule 5: Critical Blind Spot - Dynamic Programming
        int dpProblems = 0;
        for (TopicDiagnostic td : diagnostics) {
            if (td.getTopicName().toLowerCase().contains("dynamic")) {
                dpProblems += td.getTotalProblems();
            }
        }
        if (dpProblems <= 2) {
            recommendations.add(new GeneratedRecommendation(
                "Dynamic Programming",
                "BLIND_SPOT",
                "Overcome Blind Spot: Dynamic Programming Fundamentals",
                "Dynamic Programming is one of the highest-weight topics in technical screenings, yet typically under-practiced compared to Arrays. Focusing on 1D DP state transitions will convert this from a liability into a strength.",
                "Currently fewer than 3 Dynamic Programming problems logged in your active portfolio.",
                Arrays.asList(
                    "Master foundational 1D DP: Climbing Stairs, House Robber, and Coin Change",
                    "Write out the base cases and recurrence relation on paper before writing code",
                    "Practice identifying overlapping subproblems and optimal substructure"
                ),
                3
            ));
        }

        // Rule 6: Tree & Graph Traversal Depth
        int treeProblems = 0;
        for (TopicDiagnostic td : diagnostics) {
            String name = td.getTopicName().toLowerCase();
            if (name.contains("tree") || name.contains("graph")) {
                treeProblems += td.getTotalProblems();
            }
        }
        if (treeProblems <= 3) {
            recommendations.add(new GeneratedRecommendation(
                "Trees & Graphs",
                "HIGH_YIELD_TOPIC",
                "Build Depth: Tree & Graph Traversal Patterns",
                "Binary Trees and Graph traversals appear in over 30% of technical coding assessments. Deepening your recursion and queue-based BFS patterns is essential for interview readiness.",
                "Currently fewer than 4 Tree / Graph problems recorded in your portfolio.",
                Arrays.asList(
                    "Master recursive In-order, Pre-order, and Post-order traversals",
                    "Implement Breadth-First Search (BFS) using a Queue for level-order problems",
                    "Solve classic problems: Invert Binary Tree, Maximum Depth, and Number of Islands"
                ),
                2
            ));
        }

        // Rule 7: Active Reflection & Mistake Logging
        if (totalMistakes == 0) {
            recommendations.add(new GeneratedRecommendation(
                "Error Reflection",
                "MISTAKE_LOGGING",
                "Practice Discipline: Log Mistakes & Failure Modes",
                "Top competitive programmers maintain an error journal. Tagging failure reasons (Off-by-one, Edge cases, Time Limit Exceeded) in the Mistake Logger prevents repeating the same mistakes during live technical interviews.",
                "No mistakes currently logged in your error journal.",
                Arrays.asList(
                    "When a submission fails on LeetCode or in practice, tap 'Log Mistake' in CodeInsight",
                    "Tag the root cause: Edge Case, TLE, or Logic Error",
                    "Review the Analytics Error Distribution chart weekly before mock interviews"
                ),
                2
            ));
        }

        Collections.sort(recommendations, (a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
        return recommendations;
    }

    private String getAdviceForMistake(MistakeCategory category) {
        switch (category) {
            case EDGE_CASE_MISSED:
                return "Establish a pre-run checklist: test empty inputs, single element, negative values, and maximum constraints before executing.";
            case TIME_LIMIT_EXCEEDED:
                return "Analyze the time complexity upfront (O(N^2) vs O(N log N)) based on the input constraint bounds before coding.";
            case LOGICAL_ERROR:
                return "Walk through an example dry-run line-by-line using pen and paper to verify loop variables and conditionals.";
            case OFF_BY_ONE:
                return "Pay extra attention to 0-based vs 1-based indexing, '<' vs '<=', and range boundaries.";
            case RECURSION_ERROR:
                return "Double-check base condition termination and verify that parameters decrease toward the base case on every branch.";
            case SPACE_COMPLEXITY:
                return "Look for opportunities to reuse variables or switch from recursion to iteration to avoid auxiliary memory.";
            default:
                return "Document root causes in your attempt notes so similar mistakes are not repeated.";
        }
    }

    private List<String> getChecklistForCategory(MistakeCategory category) {
        switch (category) {
            case EDGE_CASE_MISSED:
                return Arrays.asList("Test input sizes: 0, 1, and N_max", "Test duplicate elements and extreme values", "Verify odd vs even lengths");
            case TIME_LIMIT_EXCEEDED:
                return Arrays.asList("Calculate expected operations from N (N <= 10^5 requires O(N) or O(N log N))", "Consider HashMaps or Two Pointers to replace nested loops", "Avoid copying large arrays inside loops");
            case RECURSION_ERROR:
                return Arrays.asList("Verify base condition is reached on all input branches", "Ensure returned values are properly propagated", "Check for stack overflow on deep inputs");
            default:
                return Arrays.asList("Write tests before submitting", "Document the lesson learned in notes");
        }
    }
}

