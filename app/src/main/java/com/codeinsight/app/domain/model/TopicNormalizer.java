package com.codeinsight.app.domain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TopicNormalizer {

    public static final List<String> PREDEFINED_TOPICS = Collections.unmodifiableList(Arrays.asList(
        "Arrays",
        "Strings",
        "Linked Lists",
        "Stacks",
        "Queues",
        "Hashing",
        "Trees",
        "Graphs",
        "Recursion",
        "Backtracking",
        "Dynamic Programming",
        "Greedy",
        "Sorting",
        "Searching",
        "Two Pointers",
        "Sliding Window",
        "Binary Search",
        "Bit Manipulation",
        "Tries",
        "Heaps",
        "Union Find",
        "Math"
    ));

    private static final Set<String> PLATFORM_KEYWORDS = new HashSet<>(Arrays.asList(
        "leetcode", "codechef", "hackerrank", "codeforces", "dsa", "platform", "platforms",
        "algorithm", "algorithms", "data structure", "data structures"
    ));

    private TopicNormalizer() {}

    /**
     * Normalizes a topic string to a canonical topic name.
     * Returns null if the topic represents a platform or generic filler.
     */
    public static String normalize(String raw) {
        if (raw == null) return null;
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) return null;

        String lower = trimmed.toLowerCase();

        // Filter out platform names and generic categories
        if (PLATFORM_KEYWORDS.contains(lower)) {
            return null;
        }

        // Direct canonical match check (case-insensitive)
        for (String canonical : PREDEFINED_TOPICS) {
            if (canonical.equalsIgnoreCase(trimmed)) {
                return canonical;
            }
        }

        // Canonical synonyms and singular/plural mapping
        switch (lower) {
            case "array":
            case "arrays":
                return "Arrays";
            case "string":
            case "strings":
                return "Strings";
            case "linked list":
            case "linked lists":
            case "linkedlist":
            case "linkedlists":
                return "Linked Lists";
            case "stack":
            case "stacks":
                return "Stacks";
            case "queue":
            case "queues":
                return "Queues";
            case "hash table":
            case "hash tables":
            case "hashmap":
            case "hash map":
            case "hashtable":
            case "hash":
            case "hashing":
                return "Hashing";
            case "tree":
            case "trees":
            case "binary tree":
            case "binary trees":
            case "binary search tree":
            case "bst":
                return "Trees";
            case "graph":
            case "graphs":
                return "Graphs";
            case "recursion":
            case "recursive":
                return "Recursion";
            case "backtracking":
            case "backtrack":
                return "Backtracking";
            case "dynamic programming":
            case "dp":
            case "1d dynamic programming":
            case "2d dynamic programming":
                return "Dynamic Programming";
            case "greedy":
                return "Greedy";
            case "sorting":
            case "sort":
                return "Sorting";
            case "searching":
            case "search":
                return "Searching";
            case "two pointers":
            case "two pointer":
            case "twopointers":
            case "two-pointers":
                return "Two Pointers";
            case "sliding window":
            case "slidingwindow":
            case "sliding-window":
                return "Sliding Window";
            case "binary search":
            case "binarysearch":
            case "binary-search":
                return "Binary Search";
            case "bit manipulation":
            case "bit-manipulation":
            case "bitwise":
            case "bits":
                return "Bit Manipulation";
            case "trie":
            case "tries":
                return "Tries";
            case "heap":
            case "heaps":
            case "priority queue":
            case "priorityqueue":
                return "Heaps";
            case "union find":
            case "unionfind":
            case "disjoint set":
            case "dsu":
                return "Union Find";
            case "math":
            case "mathematics":
                return "Math";
            default:
                // Custom topic: format title case
                if (trimmed.length() == 1) return trimmed.toUpperCase();
                return Character.toUpperCase(trimmed.charAt(0)) + trimmed.substring(1);
        }
    }
}

