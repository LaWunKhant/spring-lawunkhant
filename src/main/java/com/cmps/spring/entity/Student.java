package com.cmps.spring.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    private String id; // String because IDs look like '001001'
    private String name;
    private String gender;
    private String address;
    private Integer age;

    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    private List<StudentExam> studentExams;
}