package com.codeinsight.app.data.remote.leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeetCodeProfile {
    private String username;
    private int ranking;
    private int totalSolved;
    private int easySolved;
    private int mediumSolved;
    private int hardSolved;
    private int totalSubmissions;
    private String submissionCalendarJson;
    private List<LeetCodeResponse.RecentAcSubmission> recentSubmissions;
    private Map<String, Integer> tagSolvedCounts = new HashMap<>();

    public LeetCodeProfile(
        String username,
        int ranking,
        int totalSolved,
        int easySolved,
        int mediumSolved,
        int hardSolved,
        int totalSubmissions,
        String submissionCalendarJson,
        List<LeetCodeResponse.RecentAcSubmission> recentSubmissions,
        Map<String, Integer> tagSolvedCounts
    ) {
        this.username = username;
        this.ranking = ranking;
        this.totalSolved = totalSolved;
        this.easySolved = easySolved;
        this.mediumSolved = mediumSolved;
        this.hardSolved = hardSolved;
        this.totalSubmissions = totalSubmissions;
        this.submissionCalendarJson = submissionCalendarJson != null ? submissionCalendarJson : "{}";
        this.recentSubmissions = recentSubmissions;
        if (tagSolvedCounts != null) {
            this.tagSolvedCounts = tagSolvedCounts;
        }
    }

    public LeetCodeProfile(
        String username,
        int ranking,
        int totalSolved,
        int easySolved,
        int mediumSolved,
        int hardSolved,
        List<LeetCodeResponse.RecentAcSubmission> recentSubmissions,
        Map<String, Integer> tagSolvedCounts
    ) {
        this(username, ranking, totalSolved, easySolved, mediumSolved, hardSolved, totalSolved, "{}", recentSubmissions, tagSolvedCounts);
    }

    public LeetCodeProfile(
        String username,
        int ranking,
        int totalSolved,
        int easySolved,
        int mediumSolved,
        int hardSolved,
        List<LeetCodeResponse.RecentAcSubmission> recentSubmissions
    ) {
        this(username, ranking, totalSolved, easySolved, mediumSolved, hardSolved, totalSolved, "{}", recentSubmissions, new HashMap<>());
    }

    public String getUsername() { return username; }
    public int getRanking() { return ranking; }
    public int getTotalSolved() { return totalSolved; }
    public int getEasySolved() { return easySolved; }
    public int getMediumSolved() { return mediumSolved; }
    public int getHardSolved() { return hardSolved; }
    public int getTotalSubmissions() { return totalSubmissions; }
    public String getSubmissionCalendarJson() { return submissionCalendarJson; }
    public List<LeetCodeResponse.RecentAcSubmission> getRecentSubmissions() { return recentSubmissions; }
    public Map<String, Integer> getTagSolvedCounts() { return tagSolvedCounts; }
    public Map<String, Integer> getTagCounts() { return tagSolvedCounts; }
}
