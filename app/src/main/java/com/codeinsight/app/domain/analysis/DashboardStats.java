package com.codeinsight.app.domain.analysis;

import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import java.util.ArrayList;
import java.util.List;

public class DashboardStats {
    public int totalSolved = 0;
    public int totalAttempted = 0;
    public int easySolved = 0;
    public int mediumSolved = 0;
    public int hardSolved = 0;
    public int easyTotal = 0;
    public int mediumTotal = 0;
    public int hardTotal = 0;
    public double acceptanceRate = 0.0;
    public double averageSolvingTimeMinutes = 0.0;
    public List<DayActivity> weeklyActivity = new ArrayList<>();
    public List<ProblemWithDetails> recentProblems = new ArrayList<>();
    public List<WeakTopicSummary> weakestTopics = new ArrayList<>();
}

