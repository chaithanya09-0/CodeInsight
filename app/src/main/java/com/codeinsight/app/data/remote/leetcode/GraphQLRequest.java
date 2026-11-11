package com.codeinsight.app.data.remote.leetcode;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class GraphQLRequest {
    @SerializedName("query")
    private String query;

    @SerializedName("variables")
    private Map<String, Object> variables;

    public GraphQLRequest(String query, Map<String, Object> variables) {
        this.query = query;
        this.variables = variables;
    }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public Map<String, Object> getVariables() { return variables; }
    public void setVariables(Map<String, Object> variables) { this.variables = variables; }
}
