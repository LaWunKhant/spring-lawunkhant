package com.cmps.spring.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders") // or whatever your table name is
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderDate; // Adjust type based on your DB (e.g., LocalDate)

    // The "mappedBy" value must match the variable name in OrderProduct.java
    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;
}