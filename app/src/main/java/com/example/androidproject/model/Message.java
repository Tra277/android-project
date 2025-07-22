package com.example.androidproject.model;

public class Message {
    private String text;
    private boolean fromUser;

    public Message(String text, boolean fromUser) {
        this.text = text;
        this.fromUser = fromUser;
    }

    public String getText() {
        return text;
    }

    public boolean isFromUser() {
        return fromUser;
    }
} 