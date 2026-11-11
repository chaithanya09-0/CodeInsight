package com.codeinsight.app.domain.analysis;

public class TopicPerformance {
    private String topicName;
    private int totalProblems;
    private int solvedProblems;
    private int failedAttempts;
    private double acceptanceRate;

    public TopicPerformance(String topicName, int totalProblems, int solvedProblems, int failedAttempts, double acceptanceRate) {
        this.topicName = topicName;
        this.totalProblems = totalProblems;
        this.solvedProblems = solvedProblems;
        this.failedAttempts = failedAttempts;
        this.acceptanceRate = acceptanceRate;
    }

    public String getTopicName() { return topicName; }
    public int getTotalProblems() { return totalProblems; }
    public int getSolvedProblems() { return solvedProblems; }
    public int getSolvedCount() { return solvedProblems; }
    public int getFailedAttempts() { return failedAttempts; }
    public double getAcceptanceRate() { return acceptanceRate; }
}

