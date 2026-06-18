package com.cmps.spring.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/emp")
@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    // --- HOMEWORK COMPLIANT MAIN ENDPOINT ---
    @GetMapping("/find")
    public String find(Model model) {
        // 問1-1: 基本的なSQL文 (Repositoryの基本メソッド)
        List<Employee> employeeList = employeeRepository.findAll();
        model.addAttribute("employeeList", employeeList);
        
        // 問1-2: SELECT文 (自動実装のクエリメソッド)
        // Testing the custom query method by searching for "田中"
        List<Employee> searchResults = employeeRepository.findByName("田中");
        model.addAttribute("searchResults", searchResults);
        
        // 問1-3: SELECT文（集計関数、関数） (@Queryアノテーションでのメソッド定義)
        Double averageAge = employeeRepository.getAverageAge();
        model.addAttribute("averageAge", averageAge);
        
        return "employee/index";
    }

    // --- BUTTON ACTIONS (Keeps your links working!) ---
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/register";
    }

    @PostMapping("/insert")
    public String insertEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/emp/find";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/emp/find";
    }
    
    @GetMapping("/search")
    public String search(Model model, com.cmps.spring.form.employee.SearchForm form) {
        // Build the dynamic query using puzzle pieces
        org.springframework.data.jpa.domain.Specification<Employee> spec = 
            org.springframework.data.jpa.domain.Specification
                .where(com.cmps.spring.repository.spec.EmployeeSpecs.nameContains(form.getName()))
                .and(com.cmps.spring.repository.spec.EmployeeSpecs.ageGreaterThanEqual(form.getAgeLower()))
                .and(com.cmps.spring.repository.spec.EmployeeSpecs.ageLessThanEqual(form.getAgeUpper()))
                .and(com.cmps.spring.repository.spec.EmployeeSpecs.codeContains(form.getCode())); // Exercise Addition!

        // Execute search
        java.util.List<Employee> results = employeeRepository.findAll(spec);
        
        model.addAttribute("employeeList", results);
        return "employee/index"; // Reuses your table list view!
    }
}