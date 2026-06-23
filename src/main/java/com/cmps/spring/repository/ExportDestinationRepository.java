package com.cmps.spring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cmps.spring.entity.ExportDestination;

@Repository
public interface ExportDestinationRepository extends JpaRepository<ExportDestination, Integer> {

    // --- First Exercise Methods (Keep these!) ---
    List<ExportDestination> findByPopulationGreaterThanEqual(Integer population);
    List<ExportDestination> findByPopulationLessThan(Integer population);
    List<ExportDestination> findByCodeLessThanAndPopulationGreaterThan(Integer code, Integer population);
    List<ExportDestination> findByCodeGreaterThanEqualOrPopulationGreaterThanEqual(Integer code, Integer population);
    Optional<ExportDestination> findByName(String name);
    List<ExportDestination> findByNameContaining(String keyword);
    List<ExportDestination> findByNameIsNotNull();
    
    @Modifying
    @Query("UPDATE ExportDestination e SET e.population = :population WHERE e.name = :name")
    void updatePopulation(@Param("name") String name, @Param("population") int population);
    
    void deleteByName(String name);
    // --- Second Exercise Methods (Aggregate Functions) ---
    // 問1: 最小の人口
    @Query("SELECT MIN(e.population) FROM ExportDestination e")
    Integer getMinPopulation();

    // 問2: 最大の人口
    @Query("SELECT MAX(e.population) FROM ExportDestination e")
    Integer getMaxPopulation();

    // 問3: 人口をすべて合計
    @Query("SELECT SUM(e.population) FROM ExportDestination e")
    Long getTotalPopulation();

    // 問4: 輸出先コードが20以上の国の人口をすべて合計
    @Query("SELECT SUM(e.population) FROM ExportDestination e WHERE e.code >= 20")
    Long getTotalPopulationForCode20Plus();

    // 問5: 人口が100万人以上の国は何か国あるか
    @Query("SELECT COUNT(e) FROM ExportDestination e WHERE e.population >= 100")
    Long getCountPopulation100Plus();

    // 問6: 北洋に属する国は何か国あるか
    @Query("SELECT COUNT(e) FROM ExportDestination e WHERE e.region = '北洋'")
    Long getCountNorthOceanCountries();

    // 問7: 北洋に属する国で最大の人口
    @Query("SELECT MAX(e.population) FROM ExportDestination e WHERE e.region = '北洋'")
    Integer getMaxPopulationNorthOcean();

    // 問8: リトール王国を除いた人口を合計
    @Query("SELECT SUM(e.population) FROM ExportDestination e WHERE e.name != 'リトール王国'")
    Long getTotalPopulationExcludingLithor();

    // 問9: 平均人口が200万人以上となる地域 (GROUP BY & HAVING)
    @Query("SELECT e.region FROM ExportDestination e GROUP BY e.region HAVING AVG(e.population) >= 200")
    List<String> getRegionsWithAvgPopulation200Plus();

    // 問10: ３か国以上が属する地域
    @Query("SELECT e.region FROM ExportDestination e GROUP BY e.region HAVING COUNT(e) >= 3")
    List<String> getRegionsWithThreeOrMoreCountries();
    
    
}