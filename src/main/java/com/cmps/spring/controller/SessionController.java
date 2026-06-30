package com.cmps.spring.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/session")
public class SessionController {

    private final HttpSession session;

    @GetMapping("/shop")
    public String insertIntoCart(Model model) {
        return "cookieSession/shop";
    }

    @PostMapping("/check")
    public String saveCart(Model model, @RequestParam(required = false) String book,
            @RequestParam(required = false) String cd) {

        List<String> bookList = (LinkedList<String>) session.getAttribute("bookList");
        List<String> cdList = (LinkedList<String>) session.getAttribute("cdList");

        if (bookList == null || bookList.isEmpty()) {
            bookList = new LinkedList<String>();
        }
        if (cdList == null || cdList.isEmpty()) {
            cdList = new LinkedList<String>();
        }

        bookList.add(book);
        cdList.add(cd);

        session.setAttribute("bookList", bookList);
        session.setAttribute("cdList", cdList);

        return "redirect:/session/showCart";
    }

    @GetMapping("/showCart")
    public String showCart(Model model) {
        List<String> bookList = (LinkedList<String>) session.getAttribute("bookList");
        List<String> cdList = (LinkedList<String>) session.getAttribute("cdList");

        model.addAttribute("bookList", bookList);
        model.addAttribute("cdList", cdList);

        return "cookieSession/showCart";
    }
}