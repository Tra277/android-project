package com.example.androidproject.model;

public class ExamSet {
    private int id;
    private String name;
    private int totalCorrectAnswer;
    private int totalWrongAnswer;

    // Constructors
    public ExamSet() {
    }

    public ExamSet(int id, String name, int totalCorrectAnswer, int totalWrongAnswer) {
        this.id = id;
        this.name = name;
        this.totalCorrectAnswer = totalCorrectAnswer;
        this.totalWrongAnswer = totalWrongAnswer;
    }

    public ExamSet( String name, int totalCorrectAnswer, int totalWrongAnswer) {
        this.name = name;
        this.totalCorrectAnswer = totalCorrectAnswer;
        this.totalWrongAnswer = totalWrongAnswer;
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
}
