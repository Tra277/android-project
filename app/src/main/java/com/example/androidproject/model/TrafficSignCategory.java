package com.example.androidproject.model;

public class TrafficSignCategory {
    private int id;
    private String name;

    public TrafficSignCategory() {
    }

    public TrafficSignCategory(String name) {
        this.name = name;
    }

    public TrafficSignCategory(int id, String name) {
        this.id = id;
        this.name = name;
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
}


