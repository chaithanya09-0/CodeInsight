package com.codeinsight.app.domain.analysis;

public class DatePoint {
    private String label;
    private float value;

    public DatePoint(String label, float value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public float getValue() { return value; }
}

