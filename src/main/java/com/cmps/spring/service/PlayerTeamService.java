package com.cmps.spring.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cmps.spring.entity.PlayerTeam;
import com.cmps.spring.repository.PlayerTeamRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlayerTeamService {

    private final PlayerTeamRepository playerTeamRepository;

    public List<PlayerTeam> findAll() {
        return playerTeamRepository.findAll();
    }

    public PlayerTeam findById(Long id) {
        return playerTeamRepository.findById(id).orElse(null);
    }

    // Get team history for a specific player
    public List<PlayerTeam> getPlayerTeamHistory(Long playerId) {
        return playerTeamRepository.findByPlayerId(playerId);
    }

    @Transactional
    public void save(PlayerTeam playerTeam) {
        playerTeamRepository.save(playerTeam);
    }

    @Transactional
    public void deleteById(Long id) {
        playerTeamRepository.deleteById(id);
    }
}