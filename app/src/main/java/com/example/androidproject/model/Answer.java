package com.example.androidproject.model;

public class Answer {
    private int id;
    private boolean isCorrect;
    private String content;
    private String imagePath;
    private int questionId;

    // Constructors
    public Answer() {
    }

    public Answer(int id, boolean isCorrect, String content, String imagePath, int questionId) {
        this.id = id;
        this.isCorrect = isCorrect;
        this.content = content;
        this.imagePath = imagePath;
        this.questionId = questionId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
