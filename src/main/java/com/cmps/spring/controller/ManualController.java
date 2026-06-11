package com.cmps.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmps.spring.form.CustomForm;
import com.cmps.spring.form.ManualForm;

@Controller
@RequestMapping("/manual") // Base URL prefix
public class ManualController {

    // =========================================================================
    // 1. マニュアル フォーム① (RequestParam Version + 問1 Score Fix)
    // =========================================================================

    /**
     * 初期画面の表示
     * URL: http://localhost:8080/manual/index
     */
    @GetMapping("/index")
    public String index(Model model) {
        String pageTitle = "Spring Bootの基本的な処理の流れ";

        // Pass base data to view
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("manualText", "サーバー側から渡した文字列");
        
        return "manual/index"; // templates/manual/index.html
    }

    /**
     * 入力内容の確認（スコア判定ロジック付き）
     * URL: http://localhost:8080/manual/check
     */
    @PostMapping("/check")
    public String check(
        Model model, 
        @RequestParam String userName, 
        @RequestParam String comeFrom, 
        @RequestParam(required = false) Integer age,
        @RequestParam Integer score
    ) { 
        model.addAttribute("userName", userName);
        model.addAttribute("comeFrom", comeFrom);
        model.addAttribute("age", age);
        model.addAttribute("score", score);
        
        // Score Evaluation Logic
        String evaluationResult = (score >= 80) ? "おめでとうございます！" : "残念";
        model.addAttribute("resultMessage", evaluationResult);

        return "manual/check"; // templates/manual/check.html
    }

    // =========================================================================
    // 2. マニュアル フォーム② (ManualForm Class Version)
    // =========================================================================

    /**
     * 初期画面の表示（フォーム②Formクラス）
     * URL: http://localhost:8080/manual/index2
     */
    @GetMapping("/index2")
    public String index2(Model model) {
        model.addAttribute("form", new ManualForm());
        return "manual/index2"; // templates/manual/index2.html
    }

    /**
     * 入力内容の確認（フォーム②Formクラス）
     * URL: http://localhost:8080/manual/check2
     */
    @PostMapping("/check2")
    public String check2(Model model, @ModelAttribute("form") ManualForm form) {
        return "manual/check2"; // templates/manual/check2.html
    }

    // =========================================================================
    // 3. 練習問題 問2 (CustomForm Class Version)
    // =========================================================================

    /**
     * 練習問題用の入力画面表示
     * URL: http://localhost:8080/manual/exercise/form
     */
    @GetMapping("/exercise/form")
    public String showForm(Model model) {
        model.addAttribute("customForm", new CustomForm());
        return "exercise/formInput"; // templates/exercise/formInput.html
    }

    /**
     * 練習問題用の確認画面表示
     * URL: http://localhost:8080/manual/exercise/confirm
     */
    @PostMapping("/exercise/confirm")
    public String confirmForm(Model model, @ModelAttribute("customForm") CustomForm form) {
        model.addAttribute("customForm", form);
        return "exercise/formConfirm"; // templates/exercise/formConfirm.html
    }
}