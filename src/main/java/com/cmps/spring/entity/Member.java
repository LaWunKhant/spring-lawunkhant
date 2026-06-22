package com.cmps.spring.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data // ⬅️ This automatically creates getName(), getProfile(), etc.
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;

    // One-to-One relationship to Profile
    @OneToOne(mappedBy = "member")
    private Profile profile;

    // One-to-Many relationship to Post
    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Post> posts;
}