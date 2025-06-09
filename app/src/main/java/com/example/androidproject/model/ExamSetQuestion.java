package com.example.androidproject.model;

public class ExamSetQuestion {
    private int questionId;
    private int examSetId;

    // Constructors
    public ExamSetQuestion() {
    }

    public ExamSetQuestion(int questionId, int examSetId) {
        this.questionId = questionId;
        this.examSetId = examSetId;
    }

    // Getters and Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getExamSetId() {
        return examSetId;
    }

    public void setExamSetId(int examSetId) {
        this.examSetId = examSetId;
    }
}