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

import com.cmps.spring.entity.PlayerTeam;
import com.cmps.spring.service.PlayerService;
import com.cmps.spring.service.PlayerTeamService;
import com.cmps.spring.service.TeamService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/playerteam")
@Controller
public class PlayerTeamController {

    private final PlayerTeamService playerTeamService;
    private final PlayerService playerService;
    private final TeamService teamService;

    // List all player-team assignments
    @GetMapping("/all")
    public String showAll(Model model) {
        List<PlayerTeam> assignments = playerTeamService.findAll();
        model.addAttribute("assignments", assignments);
        return "playerteam/list";
    }

    // Show assignment form
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("assignment", new PlayerTeam());
        model.addAttribute("players", playerService.findAll());
        model.addAttribute("teams", teamService.findAll());
        return "playerteam/register";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        PlayerTeam assignment = playerTeamService.findById(id);
        model.addAttribute("assignment", assignment);
        model.addAttribute("players", playerService.findAll());
        model.addAttribute("teams", teamService.findAll());
        return "playerteam/register";
    }

    // Save assignment
    @PostMapping("/save")
    public String saveAssignment(@ModelAttribute PlayerTeam playerTeam, RedirectAttributes redirectAttributes) {
        playerTeamService.save(playerTeam);
        redirectAttributes.addFlashAttribute("successMessage", "選手チーム割り当てを保存しました。");
        return "redirect:/playerteam/all";
    }

    // Delete assignment
    @GetMapping("/delete/{id}")
    public String deleteAssignment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        playerTeamService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "割り当てを削除しました。");
        return "redirect:/playerteam/all";
    }

    // Show player's team history
    @GetMapping("/history/{playerId}")
    public String getPlayerHistory(Model model, @PathVariable Long playerId) {
        List<PlayerTeam> history = playerTeamService.getPlayerTeamHistory(playerId);
        model.addAttribute("history", history);
        model.addAttribute("playerId", playerId);
        return "playerteam/history";
    }
}