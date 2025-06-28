package com.example.androidproject.model;

public class TrafficSign {
    private int id;
    private String code;
    private String name;
    private String description;
    private String imagePath;
    private int categoryId;

    public TrafficSign() {
    }

    public TrafficSign(String code, String name, String description, String imagePath, int categoryId) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
    }

    public TrafficSign(int id, String code, String name, String description, String imagePath, int categoryId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}


