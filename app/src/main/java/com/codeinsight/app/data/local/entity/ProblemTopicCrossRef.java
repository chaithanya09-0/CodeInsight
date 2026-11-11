package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
    tableName = "problem_topic_cross_ref",
    primaryKeys = {"problemId", "topicId"},
    foreignKeys = {
        @ForeignKey(
            entity = CodingProblem.class,
            parentColumns = "id",
            childColumns = "problemId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Topic.class,
            parentColumns = "id",
            childColumns = "topicId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {@Index("topicId")}
)
public class ProblemTopicCrossRef {
    private long problemId;
    private long topicId;

    public ProblemTopicCrossRef(long problemId, long topicId) {
        this.problemId = problemId;
        this.topicId = topicId;
    }

    public long getProblemId() { return problemId; }
    public void setProblemId(long problemId) { this.problemId = problemId; }

    public long getTopicId() { return topicId; }
    public void setTopicId(long topicId) { this.topicId = topicId; }
}

