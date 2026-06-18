package com.cmps.spring.form.employee;

import lombok.Data;

@Data
public class SearchForm {
    private String name;
    private Integer ageLower;
    private Integer ageUpper;
    private String code; // Added for the exercise!
}