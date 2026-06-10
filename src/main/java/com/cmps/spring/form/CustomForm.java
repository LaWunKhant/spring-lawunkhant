package com.cmps.spring.form;

import java.io.Serializable;

public class CustomForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private String category;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}