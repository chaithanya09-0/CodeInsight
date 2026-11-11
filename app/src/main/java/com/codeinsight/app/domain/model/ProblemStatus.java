package com.codeinsight.app.domain.model;

public enum ProblemStatus {
    UNSOLVED("Unsolved"),
    ATTEMPTED("Attempted"),
    SOLVED("Solved");

    private final String displayName;

    ProblemStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ProblemStatus fromString(String value) {
        if (value == null) return UNSOLVED;
        for (ProblemStatus s : values()) {
            if (s.displayName.equalsIgnoreCase(value) || s.name().equalsIgnoreCase(value)) {
                return s;
            }
        }
        return UNSOLVED;
    }
}

