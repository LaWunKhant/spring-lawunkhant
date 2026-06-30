package com.cmps.spring.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    @Test
    @DisplayName("ID検索_検索できた時")
    void testFindById_found() throws Exception {

        Employee expected = new Employee("0001", "田中", 25);
        expected.setId(1L);

        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        Employee result = service.findById(expected.getId());

        assertEquals(result, expected);

        verify(repository, times(1)).findById(expected.getId());
    }

    @Test
    @DisplayName("ID検索_見つからなかった時")
    void testFindById_notFound() throws Exception {

        long unExistId = 1000L;
        when(repository.findById(unExistId)).thenReturn(Optional.empty());

        Employee result = service.findById(unExistId);

        assertNull(result);

        verify(repository, times(1)).findById(unExistId);
    }
}