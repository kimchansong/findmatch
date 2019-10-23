package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.TeamMember;
import com.ssafy.springserver.Entity.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {
    @Query("SELECT teamhasuser FROM TeamMember teamhasuser WHERE Team_team_name = :teamName")
    List<TeamMember> findByName(@Param("teamName") String teamName);
}
