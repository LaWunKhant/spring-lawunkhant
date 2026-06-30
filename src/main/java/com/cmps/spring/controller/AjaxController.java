package com.cmps.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/ajax")
public class AjaxController {

    private final EmployeeRepository employeeRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "ajax/index";
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @PostMapping("/find")
    @ResponseBody
    public Employee find(@RequestParam String id) {
        Optional<Employee> optEmployee = employeeRepository.findById(Long.parseLong(id));
        return optEmployee.get();
    }

    @GetMapping("/api/employees/{id}")
    @ResponseBody
    public Employee apiFindById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/employees/create")
    @ResponseBody
    public Employee apiCreate(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @PostMapping("/searchByName")
    @ResponseBody
    public List<Employee> searchByName(@RequestParam String keyword) {
        return employeeRepository.findByNameContaining(keyword);
    }
}