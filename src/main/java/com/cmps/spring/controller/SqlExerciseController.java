package com.cmps.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cmps.spring.repository.ExportDestinationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/sql")
@Controller
public class SqlExerciseController {

    private final ExportDestinationRepository exportDestinationRepository;

    @GetMapping("/exercise1")
    public String runExercise(Model model) {
        
        // --- 練習問題 1: SELECT文 (Filtering) ---
        model.addAttribute("allCountries", exportDestinationRepository.findAll());
        model.addAttribute("q1", exportDestinationRepository.findByPopulationGreaterThanEqual(100));
        model.addAttribute("q2", exportDestinationRepository.findByPopulationLessThan(100));
        model.addAttribute("q3", exportDestinationRepository.findByCodeLessThanAndPopulationGreaterThan(20, 150));
        model.addAttribute("q4", exportDestinationRepository.findByCodeGreaterThanEqualOrPopulationGreaterThanEqual(20, 200));
        exportDestinationRepository.findByName("トカンタ国").ifPresent(c -> model.addAttribute("q5Population", c.getPopulation()));
        model.addAttribute("q6", exportDestinationRepository.findByNameContaining("ン"));
        model.addAttribute("q7", exportDestinationRepository.findByNameIsNotNull());
        
        // --- 練習問題 2: 集計関数 (Aggregates) ---
        model.addAttribute("agg1", exportDestinationRepository.getMinPopulation());
        model.addAttribute("agg2", exportDestinationRepository.getMaxPopulation());
        model.addAttribute("agg3", exportDestinationRepository.getTotalPopulation());
        model.addAttribute("agg4", exportDestinationRepository.getTotalPopulationForCode20Plus());
        model.addAttribute("agg5", exportDestinationRepository.getCountPopulation100Plus());
        model.addAttribute("agg6", exportDestinationRepository.getCountNorthOceanCountries());
        model.addAttribute("agg7", exportDestinationRepository.getMaxPopulationNorthOcean());
        model.addAttribute("agg8", exportDestinationRepository.getTotalPopulationExcludingLithor());
        model.addAttribute("agg9", exportDestinationRepository.getRegionsWithAvgPopulation200Plus());
        model.addAttribute("agg10", exportDestinationRepository.getRegionsWithThreeOrMoreCountries());
        
        return "employee/sql_results";
    }
}