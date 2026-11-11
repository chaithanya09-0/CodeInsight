package com.codeinsight.app.domain.model;

public enum Platform {
    LEETCODE("LeetCode"),
    CODECHEF("CodeChef"),
    HACKERRANK("HackerRank"),
    CODEFORCES("Codeforces"),
    OTHER("Other");

    private final String displayName;

    Platform(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Platform fromString(String value) {
        if (value == null) return OTHER;
        for (Platform p : values()) {
            if (p.displayName.equalsIgnoreCase(value) || p.name().equalsIgnoreCase(value)) {
                return p;
            }
        }
        return OTHER;
    }
}

