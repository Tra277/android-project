package com.example.androidproject.model;

public class LicenseCategoryItem {
    private String title;
    private String description;

    private   String shortCode;

    public LicenseCategoryItem(String title, String description, String shortCode) {
        this.title = title;
        this.description = description;
        this.shortCode = shortCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getShortCode() {
        return shortCode;
    }
}
