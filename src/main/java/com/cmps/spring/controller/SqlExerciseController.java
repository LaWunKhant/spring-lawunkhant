package com.cmps.spring.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cmps.spring.entity.ExportDestination;
import org.springframework.transaction.annotation.Transactional;
import com.cmps.spring.repository.ExportDestinationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/sql")
@Controller
public class SqlExerciseController {

    private final ExportDestinationRepository exportDestinationRepository;

    @GetMapping("/exercise1")
    public String runExercise(Model model) {
        
        // ==========================================
        // PART 1: 全件データ表示 & データ状況の確認
        // ==========================================
        model.addAttribute("allCountries", exportDestinationRepository.findAll());
        
        // ==========================================
        // PART 2: SELECT文 練習問題 (Filtering Queries)
        // ==========================================
        // 問1: 人口が100万人以上
        model.addAttribute("q1", exportDestinationRepository.findByPopulationGreaterThanEqual(100));
        
        // 問2: 人口が100万人未満
        model.addAttribute("q2", exportDestinationRepository.findByPopulationLessThan(100));
        
        // 問3: コード20未満 かつ 人口150万人より多い
        model.addAttribute("q3", exportDestinationRepository.findByCodeLessThanAndPopulationGreaterThan(20, 150));
        
        // 問4: コード20以上 または 人口200万人以上
        model.addAttribute("q4", exportDestinationRepository.findByCodeGreaterThanEqualOrPopulationGreaterThanEqual(20, 200));
        
        // 問5: トカンタ国の人口
        exportDestinationRepository.findByName("トカンタ国")
            .ifPresent(c -> model.addAttribute("q5Population", c.getPopulation()));
            
        // 問6: 国名に「ン」を含む
        model.addAttribute("q6", exportDestinationRepository.findByNameContaining("ン"));
        
        // 問7: 輸出先名がNULLでない
        model.addAttribute("q7", exportDestinationRepository.findByNameIsNotNull());
        
        // ==========================================
        // PART 3: SELECT文（集計関数、関数） 練習問題 (Aggregates)
        // ==========================================
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

//    // --- 新しい練習問題用データ更新アクション (問2: トカンタ国アップデート) ---
//    @GetMapping("/update-tocanta")
//    public String updateTocanta() {
//        exportDestinationRepository.findAll().stream()
//            .filter(c -> "トカンタ国".equals(c.getName()))
//            .findFirst()
//            .ifPresent(c -> {
//                c.setPopulation(150);
//                exportDestinationRepository.save(c);
//            });
//        return "redirect:/sql/exercise1"; // Redirect refreshes all queries automatically!
//    }
    @GetMapping("/update-tocanta")
    @Transactional
    public String updateTocanta() {
        exportDestinationRepository.updatePopulation("トカンタ国", 150);
        return "redirect:/sql/exercise1";
    }
    

//    // --- 新しい練習問題用データ更新アクション (問3: パローヌ国削除) ---
//    @GetMapping("/delete-parone")
//    public String deleteParone() {
//        exportDestinationRepository.findAll().stream()
//            .filter(c -> "パローヌ国".equals(c.getName()))
//            .findFirst()
//            .ifPresent(c -> exportDestinationRepository.deleteById(c.getCode()));
//        return "redirect:/sql/exercise1";
//    }
    @GetMapping("/delete-parone")
    @Transactional
    public String deleteParone() {
        exportDestinationRepository.deleteByName("パローヌ国");
        return "redirect:/sql/exercise1";
    }
}