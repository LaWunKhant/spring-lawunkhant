package com.cmps.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmps.spring.form.CustomForm;

@Controller
@RequestMapping("/manual")
public class ManualController {

    // =========================================================================
    // 問1: REQUESTPARAM VERSION (MANUAL PATHS)
    // =========================================================================

    /**
     * Displays the initial manual input form.
     * URL: http://localhost:8080/manual/index
     */
    @GetMapping("/index")
    public String showIndex() {
        return "manual/index"; // Points to src/main/resources/templates/manual/index.html
    }

    /**
     * Processes the manual input form data, evaluates the score, and shows confirmation.
     * URL: http://localhost:8080/manual/check
     */
    @PostMapping("/check")
    public String check(
        Model model, 
        @RequestParam String userName, 
        @RequestParam String comeFrom, 
        @RequestParam(required = false) Integer age,
        @RequestParam Integer score // Captures the score field from index.html
    ) { 
        // Pass individual attributes back to the view model
        model.addAttribute("userName", userName);
        model.addAttribute("comeFrom", comeFrom);
        model.addAttribute("age", age);
        model.addAttribute("score", score);
        
        // --- Score Evaluation Logic ---
        String evaluationResult;
        if (score >= 80) {
            evaluationResult = "おめでとうございます！";
        } else {
            evaluationResult = "残念";
        }
        
        // Pass the calculated result text to check.html
        model.addAttribute("resultMessage", evaluationResult);

        return "manual/check"; // Points to src/main/resources/templates/manual/check.html
    }

    // =========================================================================
    // 問2: FORM CLASS VERSION (EXERCISE PATHS)
    // =========================================================================

    /**
     * Displays the complex exercise input form bound to a data object container.
     * URL: http://localhost:8080/manual/exercise/form
     */
    @GetMapping("/exercise/form")
    public String showForm(Model model) {
        // Provide an empty object instance so Thymeleaf can safely map fields natively
        model.addAttribute("customForm", new CustomForm());
        return "exercise/formInput"; // Points to src/main/resources/templates/exercise/formInput.html
    }

    /**
     * Automatically binds all input fields into a CustomForm object instance and renders it.
     * URL: http://localhost:8080/manual/exercise/confirm
     */
    @PostMapping("/exercise/confirm")
    public String confirmForm(Model model, @ModelAttribute("customForm") CustomForm form) {
        // The parameter 'form' contains title, content, and category automatically populated
        model.addAttribute("customForm", form);
        return "exercise/formConfirm"; // Points to src/main/resources/templates/exercise/formConfirm.html
    }
}	