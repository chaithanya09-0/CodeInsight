package com.codeinsight.app.data.remote.leetcode;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LeetCodeResponse {
    @SerializedName("data")
    public DataContainer data;

    public static class DataContainer {
        @SerializedName("matchedUser")
        public MatchedUser matchedUser;

        @SerializedName("recentAcSubmissionList")
        public List<RecentAcSubmission> recentAcSubmissionList;

        @SerializedName("questionList")
        public QuestionList questionList;
    }

    public static class QuestionList {
        @SerializedName("totalNum")
        public int totalNum;

        @SerializedName("data")
        public List<QuestionData> data;
    }

    public static class QuestionData {
        @SerializedName("title")
        public String title;

        @SerializedName("titleSlug")
        public String titleSlug;

        @SerializedName("difficulty")
        public String difficulty;

        @SerializedName("topicTags")
        public List<TopicTag> topicTags;
    }

    public static class TopicTag {
        @SerializedName("name")
        public String name;
    }

    public static class MatchedUser {
        @SerializedName("username")
        public String username;

        @SerializedName("profile")
        public Profile profile;

        @SerializedName("submitStats")
        public SubmitStats submitStats;

        @SerializedName("submissionCalendar")
        public String submissionCalendar;

        @SerializedName("tagProblemCounts")
        public TagProblemCounts tagProblemCounts;
    }

    public static class TagProblemCounts {
        @SerializedName("advanced")
        public List<TagCount> advanced;

        @SerializedName("intermediate")
        public List<TagCount> intermediate;

        @SerializedName("fundamental")
        public List<TagCount> fundamental;
    }

    public static class TagCount {
        @SerializedName("tagName")
        public String tagName;

        @SerializedName("problemsSolved")
        public int problemsSolved;
    }

    public static class Profile {
        @SerializedName("ranking")
        public Integer ranking;

        @SerializedName("realName")
        public String realName;
    }

    public static class SubmitStats {
        @SerializedName("acSubmissionNum")
        public List<SubmissionCount> acSubmissionNum;

        @SerializedName("totalSubmissionNum")
        public List<SubmissionCount> totalSubmissionNum;
    }

    public static class SubmissionCount {
        @SerializedName("difficulty")
        public String difficulty;

        @SerializedName("count")
        public int count;
    }

    public static class RecentAcSubmission {
        @SerializedName("title")
        public String title;

        @SerializedName("titleSlug")
        public String titleSlug;

        @SerializedName("timestamp")
        public String timestamp;
    }
}
