package com.cmps.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cmps.spring.entity.PlayerTeam;

@Repository
public interface PlayerTeamRepository extends JpaRepository<PlayerTeam, Long> {
    // Fetches the team history for a specific footballer
    List<PlayerTeam> findByPlayerId(Long playerId);
}