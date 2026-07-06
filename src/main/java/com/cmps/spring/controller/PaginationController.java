package com.cmps.spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmps.spring.entity.Country;
import com.cmps.spring.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pagination")
@RequiredArgsConstructor
public class PaginationController {

    private final CountryRepository countryRepository;

    /**
     * ページネーション基本実装
     * URLパラメータ: ?page=0&size=3 (デフォルト)
     */
    @GetMapping("")
    public String index(Model model, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        // 国の情報を検索
        Page<Country> pageList = countryRepository.findAll(pageable);
        model.addAttribute("pages", pageList);
        return "pagination/index";
    }

    /**
     * Page オブジェクトの内容を確認するAPI
     */
    @GetMapping("/show")
    public Page<Country> showPageObj(Model model, @PageableDefault(page = 0, size = 4) Pageable pageable) {
        Page<Country> pageList = countryRepository.findAll(pageable);
        return pageList;
    }
}