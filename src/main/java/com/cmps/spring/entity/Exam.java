package com.cmps.spring.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @OneToMany(mappedBy = "exam")
    @ToString.Exclude
    private List<StudentExam> studentExams;
}