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
@RequestMapping("/sql")
@Controller
public class SqlExerciseController {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/exercise1")
    public String runExercise(Model model) {
        
        // --- 問1-1: 基本的なSQL文 (Repositoryの基本メソッド) ---
        List<Employee> allEmployees = employeeRepository.findAll();
        model.addAttribute("allEmployees", allEmployees);
        
        // --- 問1-2: SELECT文 (自動実装のクエリメソッド) ---
        // Let's filter by age >= 30 as an example of a conditional SELECT
        List<Employee> seniorEmployees = employeeRepository.findByAgeGreaterThanEqual(30);
        model.addAttribute("seniorEmployees", seniorEmployees);
        
        // --- 問1-3: SELECT文（集計関数、関数） (@Queryアノテーション) ---
        Double averageAge = employeeRepository.getAverageAge();
        model.addAttribute("averageAge", averageAge);
        
        // Returns a distinct view template
        return "employee/sql_results";
    }
}