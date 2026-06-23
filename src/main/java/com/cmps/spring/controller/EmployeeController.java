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
import com.cmps.spring.service.EmployeeService; // Imported Service
import com.cmps.spring.form.employee.SearchForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/emp")
@Controller
public class EmployeeController {

    // Inject EmployeeService instead of EmployeeRepository
    private final EmployeeService employeeService;

    // --- HOMEWORK COMPLIANT MAIN ENDPOINT ---
    @GetMapping("/find")
    public String find(Model model) {
        // 問1-1: Service経由で全件取得
        List<Employee> employeeList = employeeService.findAll();
        model.addAttribute("employeeList", employeeList);
        
        // 問1-2: Service経由で名前検索
        List<Employee> searchResults = employeeService.findByName("田中");
        model.addAttribute("searchResults", searchResults);
        
        // 問1-3: Service経由で平均年齢取得
        Double averageAge = employeeService.getAverageAge();
        model.addAttribute("averageAge", averageAge);
        
        return "employee/index";
    }

    // --- BUTTON ACTIONS ---
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/register";
    }
    
    @GetMapping("/trans-index")
	public String transIndex(@ModelAttribute("successMessage") String successMessage) {////引数を追記
		
		return "employee/transition";
	}

    
    @PostMapping("/post-sample")
    public String postSample(@RequestParam String text, RedirectAttributes redirectAttributes) {////引数を追記
		System.out.println("POST通信です。");
		System.out.println("入力値：「" + text + "」");

		////追記
		// フラッシュメッセージをredirectAttributesに登録
		redirectAttributes.addFlashAttribute("successMessage", "処理が完了しました。");
		
		return "redirect:/emp/trans-index";
	}
    
    @GetMapping("/all")
    public String showAllEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employee/list"; // Maps to templates/employee/list.html
    }
    
    @GetMapping("/findone/{id}")
    public String findOne(Model model, @PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employee/index"; // Or your dedicated detail view file
    }
    
    @PostMapping("/insert")
    public String insertEmployee(@ModelAttribute Employee employee) {
        employeeService.save(employee); // Changed to Service
        return "redirect:/emp/find";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id); // Changed to Service
        return "redirect:/emp/find";
    }
    
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
        // Service経由でカスタム動的クエリ実行
        List<Employee> results = employeeService.search(
            form.getName(), 
            form.getAgeLower(), 
            form.getAgeUpper()
        );

        model.addAttribute("employeeList", results);
        return "employee/index"; 
    }
}