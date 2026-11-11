package com.codeinsight.app.domain.analysis;

import com.codeinsight.app.domain.model.Difficulty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsData {
    public List<DatePoint> problemsSolvedOverTime = new ArrayList<>();
    public List<DatePoint> acceptanceRateTrends = new ArrayList<>();
    public Map<Difficulty, Integer> difficultyDistribution = new HashMap<>();
    public List<TopicPerformance> topicPerformance = new ArrayList<>();
    public Map<String, Integer> mistakeCategoryDistribution = new HashMap<>();
    public Map<Difficulty, Double> averageDurationByDifficulty = new HashMap<>();
    public List<MonthProgress> monthlyProgress = new ArrayList<>();
}

