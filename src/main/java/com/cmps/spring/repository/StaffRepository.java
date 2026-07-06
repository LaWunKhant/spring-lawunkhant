package com.cmps.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cmps.spring.entity.Staff;

@Repository
public interface StaffRepository extends 
    JpaRepository<Staff, Long>, 
    JpaSpecificationExecutor<Staff> {
}