package com.codeinsight.app.domain.analysis;

import java.util.ArrayList;
import java.util.List;

public class GeneratedRecommendation {
    private long id;
    private String topicName;
    private String type; // WEAK_TOPIC, MISTAKE_PATTERN, PREREQUISITE_PRACTICE
    private String title;
    private String description;
    private String reasoning;
    private List<String> actionableSteps;
    private int priority; // 1 to 3

    public GeneratedRecommendation(String topicName, String type, String title, String description, String reasoning, List<String> actionableSteps, int priority) {
        this.topicName = topicName;
        this.type = type;
        this.title = title;
        this.description = description;
        this.reasoning = reasoning;
        this.actionableSteps = actionableSteps != null ? actionableSteps : new ArrayList<>();
        this.priority = priority;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTopicName() { return topicName; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getReasoning() { return reasoning; }
    public List<String> getActionableSteps() { return actionableSteps; }
    public int getPriority() { return priority; }
}

