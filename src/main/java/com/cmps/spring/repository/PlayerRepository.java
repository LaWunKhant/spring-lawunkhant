package com.cmps.spring.repository;

import java.util.List;
import java.util.Optional; // Don't forget to import this!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cmps.spring.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // Added for Rule 2: Find a player by their exact unique code string
    Optional<Player> findByCode(String code);

    // Exercise 1-2 Custom Query: Search by name
    List<Player> findByNameContaining(String name);

    // Exercise 1-3 Custom Query: Calculate average age
    @Query("SELECT AVG(p.age) FROM Player p")
    Double getAverageAge();

    // Custom Dynamic Search Query
    @Query("SELECT p FROM Player p WHERE " +
           "(:name IS NULL OR p.name LIKE %:name%) AND " +
           "(:ageLower IS NULL OR p.age >= :ageLower) AND " +
           "(:ageUpper IS NULL OR p.age <= :ageUpper)")
    List<Player> searchPlayers(@Param("name") String name, 
                               @Param("ageLower") Integer ageLower, 
                               @Param("ageUpper") Integer ageUpper);
}