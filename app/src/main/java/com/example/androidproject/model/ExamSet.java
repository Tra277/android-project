package com.example.androidproject.model;

public class ExamSet {
    private int id;
    private String name;
    private int totalCorrectAnswer;
    private int totalWrongAnswer;
    private boolean isShowed;
    private long licenseId;
    private boolean isDone;

    // Constructors
    public ExamSet() {
    }

    public ExamSet(int id, String name, int totalCorrectAnswer, int totalWrongAnswer, boolean isShowed, long licenseId, boolean isDone) {
        this.id = id;
        this.name = name;
        this.totalCorrectAnswer = totalCorrectAnswer;
        this.totalWrongAnswer = totalWrongAnswer;
        this.isShowed = isShowed;
        this.licenseId = licenseId;
        this.isDone = isDone;
    }

    public ExamSet(String name, int totalCorrectAnswer, int totalWrongAnswer, boolean isShowed, long licenseId, boolean isDone) {
        this.name = name;
        this.totalCorrectAnswer = totalCorrectAnswer;
        this.totalWrongAnswer = totalWrongAnswer;
        this.isShowed = isShowed;
        this.licenseId = licenseId;
        this.isDone = isDone;
    }

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

    public int getTotalCorrectAnswer() {
        return totalCorrectAnswer;
    }

    public void setTotalCorrectAnswer(int totalCorrectAnswer) {
        this.totalCorrectAnswer = totalCorrectAnswer;
    }

    public int getTotalWrongAnswer() {
        return totalWrongAnswer;
    }

    public void setTotalWrongAnswer(int totalWrongAnswer) {
        this.totalWrongAnswer = totalWrongAnswer;
    }

    public boolean isShowed() {
        return isShowed;
    }

    public void setShowed(boolean showed) {
        isShowed = showed;
    }

    public long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(long licenseId) {
        this.licenseId = licenseId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
