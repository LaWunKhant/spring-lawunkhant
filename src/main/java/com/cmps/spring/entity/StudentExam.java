package com.cmps.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "student_exams")
public class StudentExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "student_id")
    private String studentId;
    
    @Column(name = "exam_id")
    private Integer examId;
    
    private Integer score;

    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name = "student_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude 
    private Student student;

    @ManyToOne(targetEntity = Exam.class)
    @JoinColumn(name = "exam_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude 
    private Exam exam;
}