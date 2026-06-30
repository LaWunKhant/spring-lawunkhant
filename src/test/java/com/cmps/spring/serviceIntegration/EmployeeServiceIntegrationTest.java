package com.cmps.spring.serviceIntegration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.EmployeeRepository;
import com.cmps.spring.service.EmployeeService;

@DataJpaTest
@Import(EmployeeService.class)
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeService service;

    @Test
    @Sql("/schema-emoloyee.sql")
    @DisplayName("ID検索_検索できた時")
    void testFindById_found() throws Exception {
        
        Employee expected = new Employee("0900", "神山", 30);
        repository.save(expected);

        Employee result = service.findById(expected.getId());

        assertEquals(expected, result);
        assertNotNull(result);
    }
    
    @Test
    @Sql("/schema-emoloyee.sql")
    @DisplayName("ID検索_見つからなかった時")
    void testFindById_notFound() throws Exception {
        
        Employee result = service.findById(1L);
        
        assertNull(result);
    }
}