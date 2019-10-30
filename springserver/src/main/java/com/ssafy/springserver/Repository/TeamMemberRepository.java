package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.TeamMember;
import com.ssafy.springserver.Entity.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {
    @Query("SELECT teamhasuser FROM TeamMember teamhasuser WHERE Team_t_name = :teamName")
    List<TeamMember> findByTeamName(@Param("teamName") String teamName);

    @Query("SELECT teamhasuser FROM TeamMember  teamhasuser WHERE  User_u_id = :userId")
    List<TeamMember> findByUserId(@Param("userId") String userId);

    @Query("DELETE FROM TeamMember teamhasuser WHERE Team_t_name = :teamName")
    int deleteByTeamName(@Param("teamName") String teamName);
}
