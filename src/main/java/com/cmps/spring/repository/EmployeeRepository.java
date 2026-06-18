package com.cmps.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cmps.spring.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    // Part 2: Automatic derived query method (e.g., Select by Name)
    List<Employee> findByName(String name);

    // Part 3: Aggregate Function query method using @Query (e.g., Average Age)
    @Query("SELECT AVG(e.age) FROM Employee e")
    Double getAverageAge();
}