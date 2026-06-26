package com.cmps.spring.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "login_users")
public class User {
	
    @Id
    @Column
    private Integer id;

    @Column(length = 50)
    private String username;

    @Column(length = 1000)
    private String password;

    @Column(length = 100)
    private String email;
}