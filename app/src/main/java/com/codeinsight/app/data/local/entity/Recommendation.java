package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "recommendations",
    indices = {@Index("topicName")}
)
public class Recommendation {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String topicName;
    private String type; // WEAK_TOPIC, MISTAKE_PATTERN, PREREQUISITE_PRACTICE
    private String title;
    private String description;
    private String reasoning;
    private int priority;
    private boolean dismissed;
    private long createdAt;

    public Recommendation() {
        this.priority = 1;
        this.dismissed = false;
        this.createdAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReasoning() { return reasoning; }
    public void setReasoning(String reasoning) { this.reasoning = reasoning; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public boolean isDismissed() { return dismissed; }
    public void setDismissed(boolean dismissed) { this.dismissed = dismissed; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}

