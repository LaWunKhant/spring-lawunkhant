package com.cmps.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cmps.spring.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // This allows you to perform CRUD operations on the orders table
}