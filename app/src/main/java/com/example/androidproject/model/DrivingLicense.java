package com.example.androidproject.model;

public class DrivingLicense {
    private int id;
    private String code;
    private String description;
    private String name;

    // Constructors
    public DrivingLicense() {
    }

    public DrivingLicense(int id, String code, String description, String name) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.name = name;
    }

    public DrivingLicense(String code, String description, String name) {
        this.code = code;
        this.description = description;
        this.name = name;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
