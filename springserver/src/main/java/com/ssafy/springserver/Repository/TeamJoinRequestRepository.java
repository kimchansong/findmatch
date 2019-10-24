package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.TeamJoinRequest;
import com.ssafy.springserver.Entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequest, Long> {
    @Query("SELECT teamjoinrequest FROM TeamJoinRequest teamjoinrequest WHERE Team_t_name = :teamName")
    List<TeamJoinRequest> findByTeamName(@Param("teamName") String teamName);
}
