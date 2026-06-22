package com.cmps.spring.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;

    // The "mappedBy" value must match the variable name in OrderProduct.java
    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts;
}