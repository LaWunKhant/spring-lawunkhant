package com.cmps.spring.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmps.spring.entity.Player;
import com.cmps.spring.service.PlayerService; 
import com.cmps.spring.form.player.PlayerSearchForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/player")
@Controller
public class PlayerController {

    private final PlayerService playerService;

    // 一覧表示画面 (List View Dashboard)
    @GetMapping("/all")
    public String showAllPlayers(Model model) {
        List<Player> players = playerService.findAll();
        Double avgAge = playerService.getAverageAge();
        
        model.addAttribute("players", players);
        model.addAttribute("averageAge", avgAge != null ? avgAge : 0.0);
        return "player/list"; 
    }

    // 新規登録画面の表示 (Show Form)
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("player", new Player());
        return "player/register";
    }

    // 編集画面用の表示 (Edit Form)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Player player = playerService.findById(id);
        model.addAttribute("player", player);
        return "player/register"; // Reuses registration form view for simplicity
    }
    
    // データ保存処理：登録・更新共通 (Save / Update)
    @PostMapping("/save")
    public String savePlayer(@ModelAttribute Player player, RedirectAttributes redirectAttributes) {
        playerService.save(player); 
        redirectAttributes.addFlashAttribute("successMessage", "選手情報の保存が完了しました。");
        return "redirect:/player/all";
    }

    // 削除処理 (Delete)
    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        playerService.deleteById(id); 
        redirectAttributes.addFlashAttribute("successMessage", "選手を削除しました。");
        return "redirect:/player/all";
    }

    // 詳細表示画面 (Find One Details)
    @GetMapping("/findone/{id}")
    public String findOne(Model model, @PathVariable Long id) {
        Player player = playerService.findById(id);
        model.addAttribute("player", player);
        return "player/index"; 
    }
    	
    // 条件検索機能 (Search Filters)
    @GetMapping("/search")
    public String search(Model model, PlayerSearchForm form, RedirectAttributes redirectAttributes) {
        
        // Validation check: Ensure the lower limit isn't higher than the upper limit
        if (form.ageLower() != null && form.ageUpper() != null && form.ageLower() > form.ageUpper()) {
            redirectAttributes.addFlashAttribute("errorMessage", "年齢の下限値は上限値より小さい値を入力してください。");
            return "redirect:/player/all";
        }

        List<Player> results = playerService.search(form.name(), form.ageLower(), form.ageUpper());
        model.addAttribute("players", results);
        model.addAttribute("averageAge", playerService.getAverageAge());
        return "player/list"; 
    }
}