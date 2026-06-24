package com.cmps.spring.service;

import java.util.List;
import java.util.Optional; // Ensure this import is present
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cmps.spring.entity.Player;
import com.cmps.spring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    // Added to check for unique player registration codes
    public Optional<Player> findByCode(String code) {
        return playerRepository.findByCode(code);
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public List<Player> findByName(String name) {
        return playerRepository.findByNameContaining(name);
    }

    public Double getAverageAge() {
        return playerRepository.getAverageAge();
    }

    @Transactional
    public void save(Player player) {
        playerRepository.save(player);
    }

    @Transactional
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    public List<Player> search(String name, Integer ageLower, Integer ageUpper) {
        return playerRepository.searchPlayers(name, ageLower, ageUpper);
    }
    
    public boolean isCodeAvailable(String code, Long playerId) {
        Optional<Player> existing = findByCode(code);
        if (existing.isEmpty()) return true;
        return existing.get().getId().equals(playerId);
    }

    // Validate age range
    public boolean isValidAgeRange(Integer lower, Integer upper) {
        if (lower == null || upper == null) return true;
        return lower <= upper;
    }
}