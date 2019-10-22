package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, String> {
    @Query("SELECT team FROM Team team WHERE team_name = :teamName")
    Team findByName(@Param("teamName") String teamName);
}
