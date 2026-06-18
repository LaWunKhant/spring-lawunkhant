package com.cmps.spring.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/emp")
@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/find")
    public String find(Model model) {
        // 1. Fetch ALL records from the table instead of just ID 1
        List<Employee> employeeList = employeeRepository.findAll();
        
        // 2. Add the list to the model layout context
        model.addAttribute("employeeList", employeeList);
        
        return "employee/index";
    }
}