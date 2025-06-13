package com.example.androidproject.model;

public class Question {
    private int id;
    private String content;
    private String imagePath;
    private boolean isCriticalQuiz;
    private boolean isConfusingQuiz;
    private String questionExplanation;
    private String questionStatus = "not_yet_done";
    private int categoryId;

    // Constructors
    public Question() {
    }

    public Question(int id, String content, String imagePath, boolean isCriticalQuiz,
                    boolean isConfusingQuiz, String questionExplanation,
                    String questionStatus, int categoryId) {
        this.id = id;
        this.content = content;
        this.imagePath = imagePath;
        this.isCriticalQuiz = isCriticalQuiz;
        this.isConfusingQuiz = isConfusingQuiz;
        this.questionExplanation = questionExplanation;
        this.questionStatus = questionStatus;
        this.categoryId = categoryId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isCriticalQuiz() {
        return isCriticalQuiz;
    }

    public void setCriticalQuiz(boolean criticalQuiz) {
        isCriticalQuiz = criticalQuiz;
    }

    public boolean isConfusingQuiz() {
        return isConfusingQuiz;
    }

    public void setConfusingQuiz(boolean confusingQuiz) {
        isConfusingQuiz = confusingQuiz;
    }

    public String getQuestionExplanation() {
        return questionExplanation;
    }

    public void setQuestionExplanation(String questionExplanation) {
        this.questionExplanation = questionExplanation;
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
