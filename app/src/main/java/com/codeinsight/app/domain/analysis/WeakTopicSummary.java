package com.codeinsight.app.domain.analysis;

public class WeakTopicSummary {
    private String topicName;
    private int totalAttempts;
    private int failureCount;
    private double failureRate;
    private String dominantMistake;

    public WeakTopicSummary(String topicName, int totalAttempts, int failureCount, double failureRate, String dominantMistake) {
        this.topicName = topicName;
        this.totalAttempts = totalAttempts;
        this.failureCount = failureCount;
        this.failureRate = failureRate;
        this.dominantMistake = dominantMistake;
    }

    public String getTopicName() { return topicName; }
    public int getTotalAttempts() { return totalAttempts; }
    public int getFailureCount() { return failureCount; }
    public double getFailureRate() { return failureRate; }
    public String getDominantMistake() { return dominantMistake; }
}

