package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "study_sessions")
public class StudySession {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long startTime;
    private Long endTime;
    private int problemsSolved;
    private String notes;

    public StudySession() {
        this.startTime = System.currentTimeMillis();
        this.problemsSolved = 0;
        this.notes = "";
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public Long getEndTime() { return endTime; }
    public void setEndTime(Long endTime) { this.endTime = endTime; }

    public int getProblemsSolved() { return problemsSolved; }
    public void setProblemsSolved(int problemsSolved) { this.problemsSolved = problemsSolved; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

