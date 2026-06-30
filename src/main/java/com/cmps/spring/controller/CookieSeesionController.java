package com.cmps.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cookie")
public class CookieSeesionController {

    @GetMapping("/login")
    public String login(Model model,
            @CookieValue(name = "name", required = false) String nameCookie,
            @CookieValue(name = "pass", required = false) String passCookie) {
        model.addAttribute("name", nameCookie);
        model.addAttribute("pass", passCookie);
        return "cookieSession/login";
    }

    @PostMapping("/result")
    public String loginPost(Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String pass,
            HttpServletResponse response) {

        Cookie nameCookie = new Cookie("name", name);
        Cookie passCookie = new Cookie("pass", pass);
        nameCookie.setMaxAge(60 * 60);
        passCookie.setMaxAge(60 * 60);
        response.addCookie(nameCookie);
        response.addCookie(passCookie);

        model.addAttribute("name", name);
        model.addAttribute("pass", pass);

        return "cookieSession/result";
    }
}