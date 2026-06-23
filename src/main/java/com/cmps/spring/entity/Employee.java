package com.cmps.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4)
    private String code;

    @Column(length = 100)
    private String name;

    private Integer age;

    public Employee(String code, String name, Integer age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }

	public Employee() {
		// TODO Auto-generated constructor stub
	}

}