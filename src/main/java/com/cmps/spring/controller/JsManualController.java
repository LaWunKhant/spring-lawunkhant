package com.cmps.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JsManualController {

    @GetMapping("/jsmanual")
    public String index() {
        return "jsmanual/index";
    }
}