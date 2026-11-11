package com.codeinsight.app.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MistakeWithProblem {
    @Embedded
    public Mistake mistake;

    @Relation(
        entity = CodingProblem.class,
        parentColumn = "problemId",
        entityColumn = "id"
    )
    public CodingProblem problem;

    @Relation(
        entity = CodingAttempt.class,
        parentColumn = "attemptId",
        entityColumn = "id"
    )
    public CodingAttempt attempt;

    public MistakeWithProblem() {}

    public Mistake getMistake() {
        return mistake;
    }

    public void setMistake(Mistake mistake) {
        this.mistake = mistake;
    }

    public CodingProblem getProblem() {
        return problem;
    }

    public void setProblem(CodingProblem problem) {
        this.problem = problem;
    }

    public CodingAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(CodingAttempt attempt) {
        this.attempt = attempt;
    }
}
