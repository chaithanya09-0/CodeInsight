package com.codeinsight.app.domain.model;

public enum Difficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Difficulty fromString(String value) {
        if (value == null) return MEDIUM;
        for (Difficulty d : values()) {
            if (d.displayName.equalsIgnoreCase(value) || d.name().equalsIgnoreCase(value)) {
                return d;
            }
        }
        return MEDIUM;
    }
}

