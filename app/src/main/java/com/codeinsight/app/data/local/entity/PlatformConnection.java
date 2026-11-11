package com.codeinsight.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "platform_connections",
    indices = {@Index(value = {"platform", "username"}, unique = true)}
)
public class PlatformConnection {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String platform;
    private String username;
    private boolean isConnected;
    private boolean isActive;
    private Long lastSyncAt;
    private int totalSolved;
    private int easySolved;
    private int mediumSolved;
    private int hardSolved;
    private int ranking;
    private String tagCountsJson;

    @ColumnInfo(defaultValue = "0")
    private int totalSubmissions;

    @androidx.annotation.NonNull
    @ColumnInfo(defaultValue = "'{}'")
    private String submissionCalendarJson = "{}";

    public PlatformConnection() {
        this.isConnected = false;
        this.isActive = true;
        this.totalSubmissions = 0;
        this.submissionCalendarJson = "{}";
    }

    @Ignore
    public PlatformConnection(String platform, String username) {
        this.platform = platform;
        this.username = username;
        this.isConnected = false;
        this.isActive = true;
        this.totalSubmissions = 0;
        this.submissionCalendarJson = "{}";
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isConnected() { return isConnected; }
    public void setConnected(boolean connected) { isConnected = connected; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Long getLastSyncAt() { return lastSyncAt; }
    public void setLastSyncAt(Long lastSyncAt) { this.lastSyncAt = lastSyncAt; }

    public int getTotalSolved() { return totalSolved; }
    public void setTotalSolved(int totalSolved) { this.totalSolved = totalSolved; }

    public int getEasySolved() { return easySolved; }
    public void setEasySolved(int easySolved) { this.easySolved = easySolved; }

    public int getMediumSolved() { return mediumSolved; }
    public void setMediumSolved(int mediumSolved) { this.mediumSolved = mediumSolved; }

    public int getHardSolved() { return hardSolved; }
    public void setHardSolved(int hardSolved) { this.hardSolved = hardSolved; }

    public int getRanking() { return ranking; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    public String getTagCountsJson() { return tagCountsJson; }
    public void setTagCountsJson(String tagCountsJson) { this.tagCountsJson = tagCountsJson; }

    public int getTotalSubmissions() { return totalSubmissions; }
    public void setTotalSubmissions(int totalSubmissions) { this.totalSubmissions = totalSubmissions; }

    public String getSubmissionCalendarJson() { return submissionCalendarJson; }
    public void setSubmissionCalendarJson(String submissionCalendarJson) {
        this.submissionCalendarJson = submissionCalendarJson != null ? submissionCalendarJson : "{}";
    }
}
