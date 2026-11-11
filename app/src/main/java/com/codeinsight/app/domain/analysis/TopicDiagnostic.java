package com.codeinsight.app.domain.analysis;

import com.codeinsight.app.domain.model.MistakeCategory;

public class TopicDiagnostic {
    private String topicName;
    private int totalProblems;
    private int totalAttempts;
    private int solvedCount;
    private int failedCount;
    private double acceptanceRate;
    private boolean isDataSufficient;
    private MistakeCategory dominantMistake;
    private int dominantMistakeCount;
    private String statusRisk; // Critical, Needs Work, Adequate, Strong, Insufficient Data
    private String rationale;

    public TopicDiagnostic(String topicName, int totalProblems, int totalAttempts, int solvedCount, int failedCount, double acceptanceRate, boolean isDataSufficient, MistakeCategory dominantMistake, int dominantMistakeCount, String statusRisk) {
        this(topicName, totalProblems, totalAttempts, solvedCount, failedCount, acceptanceRate, isDataSufficient, dominantMistake, dominantMistakeCount, statusRisk, "");
    }

    public TopicDiagnostic(String topicName, int totalProblems, int totalAttempts, int solvedCount, int failedCount, double acceptanceRate, boolean isDataSufficient, MistakeCategory dominantMistake, int dominantMistakeCount, String statusRisk, String rationale) {
        this.topicName = topicName;
        this.totalProblems = totalProblems;
        this.totalAttempts = totalAttempts;
        this.solvedCount = solvedCount;
        this.failedCount = failedCount;
        this.acceptanceRate = acceptanceRate;
        this.isDataSufficient = isDataSufficient;
        this.dominantMistake = dominantMistake;
        this.dominantMistakeCount = dominantMistakeCount;
        this.statusRisk = statusRisk;
        this.rationale = rationale;
    }

    public String getTopicName() { return topicName; }
    public int getTotalProblems() { return totalProblems; }
    public int getTotalAttempts() { return totalAttempts; }
    public int getSolvedCount() { return solvedCount; }
    public int getFailedCount() { return failedCount; }
    public double getAcceptanceRate() { return acceptanceRate; }
    public boolean isDataSufficient() { return isDataSufficient; }
    public MistakeCategory getDominantMistake() { return dominantMistake; }
    public int getDominantMistakeCount() { return dominantMistakeCount; }
    public String getStatusRisk() { return statusRisk; }
    public String getRationale() { return rationale; }
    public void setRationale(String rationale) { this.rationale = rationale; }
}

