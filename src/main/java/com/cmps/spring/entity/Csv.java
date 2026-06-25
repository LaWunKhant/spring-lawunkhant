package com.cmps.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "csvs")
public class Csv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private Integer age;
    private String address;

    public Csv(String code, String name, Integer age, String address) {
        this.code = code;
        this.name = name;
        this.age = age;
        this.address = address;
    }
}