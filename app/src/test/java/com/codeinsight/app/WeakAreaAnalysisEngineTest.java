package com.codeinsight.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.domain.analysis.GeneratedRecommendation;
import com.codeinsight.app.domain.analysis.TopicDiagnostic;
import com.codeinsight.app.domain.analysis.WeakAreaAnalysisEngine;
import com.codeinsight.app.domain.model.MistakeCategory;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeakAreaAnalysisEngineTest {

    private WeakAreaAnalysisEngine engine;

    @Before
    public void setUp() {
        engine = new WeakAreaAnalysisEngine();
    }

    @Test
    public void testInsufficientDataReturnsEmptyRecommendations() {
        // Only 1 problem with 1 attempt
        ProblemWithDetails pwd = createProblem(1, "Two Sum", "Arrays", "Easy");
        addAttempt(pwd, 1, false);

        List<GeneratedRecommendation> recs = engine.generateRecommendations(Collections.singletonList(pwd));
        assertTrue("Recommendations should be empty when data is insufficient", recs.isEmpty());

        List<TopicDiagnostic> diags = engine.evaluateTopics(Collections.singletonList(pwd));
        assertEquals(1, diags.size());
        assertFalse(diags.get(0).isDataSufficient());
        assertEquals("Insufficient Data", diags.get(0).getStatusRisk());
    }

    @Test
    public void testCriticalWeakTopicDetection() {
        // Create 2 problems in "Dynamic Programming" with 4 failed attempts and 1 accepted (20% acceptance)
        List<ProblemWithDetails> problems = new ArrayList<>();

        ProblemWithDetails p1 = createProblem(1, "Climbing Stairs", "Dynamic Programming", "Easy");
        addAttempt(p1, 101, false);
        addAttempt(p1, 102, false);
        addAttempt(p1, 103, true);
        p1.problem.setStatus("Solved");
        problems.add(p1);

        ProblemWithDetails p2 = createProblem(2, "Coin Change", "Dynamic Programming", "Medium");
        addAttempt(p2, 201, false);
        addAttempt(p2, 202, false);
        problems.add(p2);

        List<TopicDiagnostic> diags = engine.evaluateTopics(problems);
        assertEquals(1, diags.size());
        TopicDiagnostic dpDiag = diags.get(0);

        assertTrue("Should have sufficient data", dpDiag.isDataSufficient());
        assertEquals(20.0, dpDiag.getAcceptanceRate(), 0.01);
        assertEquals("Critical", dpDiag.getStatusRisk());

        List<GeneratedRecommendation> recs = engine.generateRecommendations(problems);
        assertFalse("Should generate recommendations", recs.isEmpty());

        GeneratedRecommendation rec = recs.get(0);
        assertEquals("Dynamic Programming", rec.getTopicName());
        assertEquals("WEAK_TOPIC", rec.getType());
        assertTrue(rec.getReasoning().contains("failures"));
        assertNotNull(rec.getActionableSteps());
        assertFalse(rec.getActionableSteps().isEmpty());
    }

    @Test
    public void testRecurringMistakePatternDetection() {
        List<ProblemWithDetails> problems = new ArrayList<>();

        ProblemWithDetails p1 = createProblem(1, "Binary Search", "Arrays", "Easy");
        addAttempt(p1, 1, false);
        addMistake(p1, 101, 1, MistakeCategory.OFF_BY_ONE.getDisplayName());
        problems.add(p1);

        ProblemWithDetails p2 = createProblem(2, "Search Insert", "Arrays", "Easy");
        addAttempt(p2, 2, false);
        addMistake(p2, 102, 2, MistakeCategory.OFF_BY_ONE.getDisplayName());
        problems.add(p2);

        ProblemWithDetails p3 = createProblem(3, "Find Peak", "Arrays", "Medium");
        addAttempt(p3, 3, true);
        p3.problem.setStatus("Solved");
        problems.add(p3);

        List<GeneratedRecommendation> recs = engine.generateRecommendations(problems);

        boolean foundMistakePattern = false;
        for (GeneratedRecommendation r : recs) {
            if ("MISTAKE_PATTERN".equals(r.getType()) && r.getTitle().contains(MistakeCategory.OFF_BY_ONE.getDisplayName())) {
                foundMistakePattern = true;
                assertTrue(r.getReasoning().contains("2 occurrences"));
                break;
            }
        }
        assertTrue("Should detect recurring Off-by-one error mistake pattern", foundMistakePattern);
    }

    @Test
    public void testPrerequisitePracticeRule() {
        List<ProblemWithDetails> problems = new ArrayList<>();

        // Recursion: 3 attempts, all failed (0% acceptance)
        ProblemWithDetails pRec = createProblem(1, "Factorial Recursion", "Recursion", "Easy");
        addAttempt(pRec, 1, false);
        addAttempt(pRec, 2, false);
        addAttempt(pRec, 3, false);
        problems.add(pRec);

        // Backtracking: 1 attempt
        ProblemWithDetails pBack = createProblem(2, "N-Queens", "Backtracking", "Hard");
        addAttempt(pBack, 4, false);
        problems.add(pBack);

        List<GeneratedRecommendation> recs = engine.generateRecommendations(problems);

        boolean foundPrereq = false;
        for (GeneratedRecommendation r : recs) {
            if ("PREREQUISITE_PRACTICE".equals(r.getType())) {
                foundPrereq = true;
                assertTrue(r.getDescription().contains("Recursion"));
                assertTrue(r.getDescription().contains("Backtracking"));
                break;
            }
        }
        assertTrue("Should recommend recursion prerequisite before backtracking", foundPrereq);
    }

    // Helper methods
    private ProblemWithDetails createProblem(long id, String title, String topicName, String difficulty) {
        ProblemWithDetails pwd = new ProblemWithDetails();
        pwd.problem = new CodingProblem();
        pwd.problem.setId(id);
        pwd.problem.setTitle(title);
        pwd.problem.setDifficulty(difficulty);
        pwd.problem.setPlatform("LeetCode");
        pwd.problem.setStatus("Attempted");

        Topic topic = new Topic(topicName, "General");
        topic.setId(id * 10);
        pwd.topics = new ArrayList<>(Collections.singletonList(topic));
        pwd.attempts = new ArrayList<>();
        pwd.mistakes = new ArrayList<>();
        return pwd;
    }

    private void addAttempt(ProblemWithDetails pwd, long attemptId, boolean accepted) {
        CodingAttempt att = new CodingAttempt();
        att.setId(attemptId);
        att.setProblemId(pwd.problem.getId());
        att.setAccepted(accepted);
        att.setDurationMinutes(15);
        att.setLanguage("Java");
        pwd.attempts.add(att);
    }

    private void addMistake(ProblemWithDetails pwd, long mistakeId, long attemptId, String category) {
        Mistake m = new Mistake();
        m.setId(mistakeId);
        m.setProblemId(pwd.problem.getId());
        m.setAttemptId(attemptId);
        m.setCategory(category);
        pwd.mistakes.add(m);
    }
}

