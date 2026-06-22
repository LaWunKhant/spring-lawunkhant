package com.cmps.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cmps.spring.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // This allows you to perform CRUD operations on the products table
}