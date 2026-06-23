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

import com.cmps.spring.entity.Team;
import com.cmps.spring.service.TeamService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/team")
@Controller
public class TeamController {

    private final TeamService teamService;

    // List all teams
    @GetMapping("/all")
    public String showAllTeams(Model model) {
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
        return "team/list";
    }

    // Show registration form
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("team", new Team());
        return "team/register";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        return "team/register";
    }

    // Save team
    @PostMapping("/save")
    public String saveTeam(@ModelAttribute Team team, RedirectAttributes redirectAttributes) {
        teamService.save(team);
        redirectAttributes.addFlashAttribute("successMessage", "チーム情報を保存しました。");
        return "redirect:/team/all";
    }

    // Delete team
    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        teamService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "チームを削除しました。");
        return "redirect:/team/all";
    }

    // Show team details
    @GetMapping("/findone/{id}")
    public String findOne(Model model, @PathVariable Long id) {
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        return "team/index";
    }
}