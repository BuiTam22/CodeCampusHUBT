package com.codecampushubt.NCKH2024TQQD.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long TagId;
    private String Description;
    private int UsageCount;

    public long getTagId() {
        return TagId;
    }

    public void setTagId(long tagId) {
        TagId = tagId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getUsageCount() {
        return UsageCount;
    }

    public void setUsageCount(int usageCount) {
        UsageCount = usageCount;
    }
}
