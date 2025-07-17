package com.example.androidproject.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private int id;
    private String content;
    private String imagePath;
    private boolean isCriticalQuiz;
    private boolean isConfusingQuiz;
    private String questionExplanation;
    private String questionStatus = "not_yet_done";
    private int categoryId;
    private int selectedAnswerId = -1; 

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

    public int getSelectedAnswerId() {
        return selectedAnswerId;
    }

    public void setSelectedAnswerId(int selectedAnswerId) {
        this.selectedAnswerId = selectedAnswerId;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        content = in.readString();
        imagePath = in.readString();
        isCriticalQuiz = in.readByte() != 0;
        isConfusingQuiz = in.readByte() != 0;
        questionExplanation = in.readString();
        questionStatus = in.readString();
        categoryId = in.readInt();
        selectedAnswerId = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeString(imagePath);
        dest.writeByte((byte) (isCriticalQuiz ? 1 : 0));
        dest.writeByte((byte) (isConfusingQuiz ? 1 : 0));
        dest.writeString(questionExplanation);
        dest.writeString(questionStatus);
        dest.writeInt(categoryId);
        dest.writeInt(selectedAnswerId);
    }
}
