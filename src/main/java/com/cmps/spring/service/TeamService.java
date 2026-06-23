package com.cmps.spring.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cmps.spring.entity.Team;
import com.cmps.spring.repository.TeamRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Transactional
    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }
}