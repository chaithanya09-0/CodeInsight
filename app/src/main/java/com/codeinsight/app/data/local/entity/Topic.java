package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "topics",
    indices = {@Index(value = {"name"}, unique = true)}
)
public class Topic {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String category;

    public Topic(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}

