package com.cmps.spring.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cmps.spring.service.impl.UserDetailsImpl;

@Controller
public class AuthController {
	
    @GetMapping("/auth")
    public String showAuth(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails != null) {
            System.out.println("ユーザー名: " + userDetails.getUsername());
        } else {
            System.out.println("未ログインです。");
        }

        return "login/auth";
    }
}