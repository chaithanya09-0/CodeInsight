package com.codeinsight.app.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.ArrayList;
import java.util.List;

public class ProblemWithDetails {
    @Embedded
    public CodingProblem problem;

    @Relation(
        parentColumn = "id",
        entityColumn = "problemId"
    )
    public List<CodingAttempt> attempts = new ArrayList<>();

    @Relation(
        parentColumn = "id",
        entityColumn = "problemId"
    )
    public List<Mistake> mistakes = new ArrayList<>();

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = @Junction(
            value = ProblemTopicCrossRef.class,
            parentColumn = "problemId",
            entityColumn = "topicId"
        )
    )
    public List<Topic> topics = new ArrayList<>();

    public String getFormattedTopics() {
        if (topics == null || topics.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < topics.size(); i++) {
            sb.append(topics.get(i).getName());
            if (i < topics.size() - 1) sb.append(" • ");
        }
        return sb.toString();
    }

    /**
     * Determines status dynamically from attempt records rather than relying on a static column.
     * Attempt with accepted == true -> "Solved"
     * Attempts exist but none accepted -> "Attempted"
     * Zero attempts recorded -> "Unsolved"
     */
    public String getCalculatedStatus() {
        if (attempts == null || attempts.isEmpty()) {
            return "Unsolved";
        }
        for (CodingAttempt a : attempts) {
            if (a.isAccepted()) {
                return "Solved";
            }
        }
        return "Attempted";
    }

    public boolean isSolved() {
        return "Solved".equals(getCalculatedStatus());
    }

    public int getAcceptedAttemptsCount() {
        if (attempts == null) return 0;
        int count = 0;
        for (CodingAttempt a : attempts) {
            if (a.isAccepted()) count++;
        }
        return count;
    }

    public int getTotalAttemptsCount() {
        return attempts != null ? attempts.size() : 0;
    }

    public long getLatestActivityTimestamp() {
        long latest = problem != null ? problem.getUpdatedAt() : 0;
        if (attempts != null) {
            for (CodingAttempt a : attempts) {
                if (a.getAttemptedAt() > latest) {
                    latest = a.getAttemptedAt();
                }
            }
        }
        return latest;
    }
}

