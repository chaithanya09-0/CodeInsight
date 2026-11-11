package com.codeinsight.app.domain.model;

public enum MistakeCategory {
    LOGICAL_ERROR("Logical error"),
    SYNTAX_ERROR("Syntax error"),
    EDGE_CASE_MISSED("Edge case missed"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded"),
    SPACE_COMPLEXITY("Space complexity issue"),
    INCORRECT_ALGORITHM("Incorrect algorithm"),
    OFF_BY_ONE("Off-by-one error"),
    RECURSION_ERROR("Recursion error"),
    OTHER("Other");

    private final String displayName;

    MistakeCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MistakeCategory fromString(String value) {
        if (value == null) return OTHER;
        for (MistakeCategory m : values()) {
            if (m.displayName.equalsIgnoreCase(value) || m.name().equalsIgnoreCase(value)) {
                return m;
            }
        }
        return OTHER;
    }
}

