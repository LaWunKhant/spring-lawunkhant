package com.cmps.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cmps.spring.entity.Country;

@Repository
public interface CountryRepository extends 
    JpaRepository<Country, Long>, 
    JpaSpecificationExecutor<Country> {
}