package com.cmps.spring.dto;

public class TextOpeDto {
    private String name;
    private String content;

    // These getters are required for Thymeleaf!
    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }
}