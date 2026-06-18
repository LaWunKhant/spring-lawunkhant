package com.cmps.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cmps.spring.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    // Previous exercise method
    List<Employee> findByName(String name);

    // --- ADD THIS LINE FOR THE NEW EXERCISE ---
    List<Employee> findByAgeGreaterThanEqual(Integer age);

    // Part 3: Aggregate Function query method using @Query
    @Query("SELECT AVG(e.age) FROM Employee e")
    Double getAverageAge();
}