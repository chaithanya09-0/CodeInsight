package com.codeinsight.app.domain.analysis;

public class DayActivity {
    private String dayName;
    private String dateString;
    private int solvedCount;
    private int attemptCount;

    public DayActivity(String dayName, String dateString, int solvedCount, int attemptCount) {
        this.dayName = dayName;
        this.dateString = dateString;
        this.solvedCount = solvedCount;
        this.attemptCount = attemptCount;
    }

    public String getDayName() { return dayName; }
    public String getDateString() { return dateString; }
    public int getSolvedCount() { return solvedCount; }
    public int getAttemptCount() { return attemptCount; }
}

