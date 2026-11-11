package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "mistakes",
    foreignKeys = {
        @ForeignKey(
            entity = CodingAttempt.class,
            parentColumns = "id",
            childColumns = "attemptId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = CodingProblem.class,
            parentColumns = "id",
            childColumns = "problemId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("attemptId"),
        @Index("problemId"),
        @Index("category")
    }
)
public class Mistake {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long attemptId;
    private long problemId;
    private String category;
    private String description;
    private String notes;
    private long createdAt;

    public Mistake() {
        this.description = "";
        this.notes = "";
        this.createdAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAttemptId() { return attemptId; }
    public void setAttemptId(long attemptId) { this.attemptId = attemptId; }

    public long getProblemId() { return problemId; }
    public void setProblemId(long problemId) { this.problemId = problemId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}

