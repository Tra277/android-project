package com.example.androidproject.model;

public class Category {
    private int id;
    private String name;
    private String description;
    private int licenseId;
    private int totalQuestions;
    private int doneQuestions;

    // Constructors
    public Category() {
    }

    public Category(int id, String name, String description, int licenseId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.licenseId = licenseId;
    }

    public Category(String name, String description, int licenseId) {
        this.name = name;
        this.description = description;
        this.licenseId = licenseId;
    }

    public Category(int id, String name, String description, int licenseId, int totalQuestions, int doneQuestions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.licenseId = licenseId;
        this.totalQuestions = totalQuestions;
        this.doneQuestions = doneQuestions;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getDoneQuestions() {
        return doneQuestions;
    }

    public void setDoneQuestions(int doneQuestions) {
        this.doneQuestions = doneQuestions;
    }
}
