package com.cmps.spring.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmps.spring.entity.Employee;
import com.cmps.spring.service.EmployeeService; 
import com.cmps.spring.form.employee.SearchForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/emp")
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    // --- MAIN DASHBOARD / LIST ENDPOINT ---
    @GetMapping("/all")
    public String showAllEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employee/list"; // Maps to templates/employee/list.html
    }

    // --- HOMEWORK COMPLIANT SEARCH ENDPOINT ---
    @GetMapping("/find")
    public String find(Model model) {
        List<Employee> employeeList = employeeService.findAll();
        model.addAttribute("employeeList", employeeList);
        
        List<Employee> searchResults = employeeService.findByName("田中");
        model.addAttribute("searchResults", searchResults);
        
        Double averageAge = employeeService.getAverageAge();
        model.addAttribute("averageAge", averageAge);
        
        return "employee/index";
    }

    // --- REGISTER FORM ---
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/register";
    }
    
    // --- INSERT (WITH REDIRECT & FLASH MESSAGE) ---
    @PostMapping("/insert")
    public String insertEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        employeeService.save(employee); 
        // Passes the success message to the list view as required by your manual
        redirectAttributes.addFlashAttribute("successMessage", "登録が完了しました。");
        return "redirect:/emp/all";
    }

    // --- DELETE (WITH REDIRECT & FLASH MESSAGE) ---
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteById(id); 
        redirectAttributes.addFlashAttribute("successMessage", "削除が完了しました。");
        return "redirect:/emp/all";
    }

    // --- FIND ONE DETAIL ---
    @GetMapping("/findone/{id}")
    public String findOne(Model model, @PathVariable Long id) {
        // Fetches your single entity safely via service
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employee/index"; 
    }
    
    // --- TRANSITION PAGES & SAMPLES ---
    @GetMapping("/trans-index")
    public String transIndex(@ModelAttribute("successMessage") String successMessage) {
        return "employee/transition";
    }
    
    @PostMapping("/post-sample")
    public String postSample(@RequestParam String text, RedirectAttributes redirectAttributes) {
        System.out.println("POST通信です。");
        System.out.println("入力値：「" + text + "」");

        redirectAttributes.addFlashAttribute("successMessage", "処理が完了しました。");
        return "redirect:/emp/trans-index";
    }
    
    @GetMapping("/transaction-sample")
    public String doTransaction(Model model) {
        try {
            employeeService.doTransactionSample(111L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/emp/all";
    }
    	
    @GetMapping("/search")
    public String search(Model model, SearchForm form) {
        List<Employee> results = employeeService.search(
            form.getName(), 
            form.getAgeLower(), 
            form.getAgeUpper()
        );

        model.addAttribute("employeeList", results);
        return "employee/index"; 
    }
}