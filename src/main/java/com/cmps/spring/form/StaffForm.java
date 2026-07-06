package com.cmps.spring.form;

import com.cmps.spring.enums.Gender;
import lombok.Data;

@Data
public class StaffForm {
    private String name;
    private Gender gender;  // Enum型で受け取る
}