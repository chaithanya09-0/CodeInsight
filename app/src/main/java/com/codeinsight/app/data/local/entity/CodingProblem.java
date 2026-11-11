package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "coding_problems",
    indices = {
        @Index(value = {"platform"}),
        @Index(value = {"difficulty"}),
        @Index(value = {"status"}),
        @Index(value = {"accountUsername"}),
        @Index(value = {"externalId"}, unique = true)
    }
)
public class CodingProblem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String url;
    private String platform;
    private String difficulty;
    private String status;
    private int totalAttempts;
    private String notes;
    private String externalId;
    @androidx.annotation.NonNull
    @androidx.room.ColumnInfo(defaultValue = "")
    private String accountUsername = "";
    private long createdAt;
    private long updatedAt;

    public CodingProblem() {
        this.status = "Unsolved";
        this.totalAttempts = 0;
        this.url = "";
        this.notes = "";
        this.accountUsername = "";
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(int totalAttempts) { this.totalAttempts = totalAttempts; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getAccountUsername() { return accountUsername != null ? accountUsername : ""; }
    public void setAccountUsername(String accountUsername) { this.accountUsername = accountUsername != null ? accountUsername : ""; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}

