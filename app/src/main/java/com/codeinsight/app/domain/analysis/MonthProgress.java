package com.codeinsight.app.domain.analysis;

public class MonthProgress {
    private String monthName;
    private int solvedCount;
    private int attemptsCount;
    private double hoursSpent;

    public MonthProgress(String monthName, int solvedCount, int attemptsCount, double hoursSpent) {
        this.monthName = monthName;
        this.solvedCount = solvedCount;
        this.attemptsCount = attemptsCount;
        this.hoursSpent = hoursSpent;
    }

    public String getMonthName() { return monthName; }
    public int getSolvedCount() { return solvedCount; }
    public int getAttemptsCount() { return attemptsCount; }
    public double getHoursSpent() { return hoursSpent; }
}

