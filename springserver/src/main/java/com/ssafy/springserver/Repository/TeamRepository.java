package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, String> {
    @Query("SELECT team FROM Team team WHERE t_name = :teamName")
    Team findByTeamName(@Param("teamName") String teamName);

    @Query("DELETE FROM Team team WHERE t_name = :teamName")
    int deleteTeam(@Param("teamName") String teamName);
}
