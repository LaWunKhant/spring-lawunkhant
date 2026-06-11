package com.cmps.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class ThymeleafController {
    
    // 「/thymeleaf」へアクセスがあった場合
    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        // リンク用サンプル
        model.addAttribute("user_id", 999);

        //  選択オブジェクト用サンプル　追記
        User user = new User("野口英世",1000); // 追記
        model.addAttribute("user",user);

        // 画面に出力するViewを指定
        return "thymeleaf/usage";
    }
}

// 以下を追記
class User{
    
    // コンストラクタ
    public User(String name, int bill) {
        this.name = name;
        this.bill = bill;
    }
    
    // 名前
    public String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // 紙幣
    public int bill;
    
    public int getBill() {
        return bill;
    }
    
    public void setBill(int bill) {
        this.bill = bill;
    }
}
