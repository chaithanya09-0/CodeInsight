package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "coding_attempts",
    foreignKeys = @ForeignKey(
        entity = CodingProblem.class,
        parentColumns = "id",
        childColumns = "problemId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("problemId")}
)
public class CodingAttempt {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long problemId;
    private boolean accepted;
    private Integer durationMinutes;
    private String language;
    private String notes;
    private long attemptedAt;

    public CodingAttempt() {
        this.language = "Java";
        this.notes = "";
        this.attemptedAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getProblemId() { return problemId; }
    public void setProblemId(long problemId) { this.problemId = problemId; }

    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public long getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(long attemptedAt) { this.attemptedAt = attemptedAt; }
}

