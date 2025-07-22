package com.example.androidproject.network.model;

import java.util.List;

public class GenerateContentRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    public GenerateContentRequest(List<Content> contents, GenerationConfig generationConfig) {
        this.contents = contents;
        this.generationConfig = generationConfig;
    }

    public static class Content {
        public String role;
        public List<Part> parts;

        public Content(String role, List<Part> parts) {
            this.role = role;
            this.parts = parts;
        }
    }

    public static class Part {
        public String text;

        public Part(String text) { this.text = text; }
    }

    public static class GenerationConfig {
        public double temperature;
        public int maxOutputTokens;

        public GenerationConfig(double temperature, int maxOutputTokens) {
            this.temperature = temperature;
            this.maxOutputTokens = maxOutputTokens;
        }
    }
} 