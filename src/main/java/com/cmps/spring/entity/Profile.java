package com.cmps.spring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data // ⬅️ Automatically creates getMember(), getAddress(), etc.
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String address;

    @OneToOne
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;
}