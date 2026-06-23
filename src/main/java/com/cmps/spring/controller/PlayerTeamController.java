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

    // List all player-team assignments (Optional / Admin use)
    @GetMapping("/all")
    public String showAll(Model model) {
        List<PlayerTeam> assignments = playerTeamService.findAll();
        model.addAttribute("assignments", assignments);
        return "playerteam/list";
    }

    // NEW: Show assignment form on a separate page for a specific player
    @GetMapping("/register/{playerId}")
    public String registerFormForPlayer(@PathVariable Long playerId, Model model) {
        PlayerTeam assignment = new PlayerTeam();
        // Link this new assignment context to our current player automatically
        assignment.setPlayer(playerService.findById(playerId));
        
        model.addAttribute("assignment", assignment);
        model.addAttribute("allTeams", teamService.findAll()); // Populates the dropdown list
        return "playerteam/register";
    }

    // UPDATED: Show edit form on a separate page
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        PlayerTeam assignment = playerTeamService.findById(id);
        
        model.addAttribute("assignment", assignment);
        model.addAttribute("allTeams", teamService.findAll()); // Populates the dropdown list
        return "playerteam/register";
    }

    // SAVING: Saves both new additions and updates, then redirects back to the Player Details profile
    @PostMapping("/save")
    public String saveAssignment(@ModelAttribute PlayerTeam playerTeam, RedirectAttributes redirectAttributes) {
        playerTeamService.save(playerTeam);
        redirectAttributes.addFlashAttribute("successMessage", "所属チーム情報を保存しました。");
        
        // Smart Redirect: Send them directly back to the specific Player Detail profile page!
        if (playerTeam.getPlayer() != null && playerTeam.getPlayer().getId() != null) {
            return "redirect:/player/findone/" + playerTeam.getPlayer().getId();
        }
        
        return "redirect:/playerteam/all";
    }

    // UPDATED DELETION: Drops the record and returns seamlessly back to the Player Details profile
    @GetMapping("/delete/{id}")
    public String deleteAssignment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // 1. Look up the relationship first to grab the associated playerId
        PlayerTeam assignment = playerTeamService.findById(id);
        Long playerId = (assignment != null && assignment.getPlayer() != null) ? assignment.getPlayer().getId() : null;
        
        // 2. Perform the actual removal
        playerTeamService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "割り当てを削除しました。");
        
        // 3. Bounce back to the active player's detail profile view if available
        if (playerId != null) {
            return "redirect:/player/findone/" + playerId;
        }
        return "redirect:/playerteam/all";
    }

    // Show player's team history view
    @GetMapping("/history/{playerId}")
    public String getPlayerHistory(Model model, @PathVariable Long playerId) {
        List<PlayerTeam> history = playerTeamService.getPlayerTeamHistory(playerId);
        model.addAttribute("history", history);
        model.addAttribute("playerId", playerId);
        return "playerteam/history";
    }
}