package com.cmps.spring.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cmps.spring.entity.Player;
import com.cmps.spring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

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
}