package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.*;
import com.ssafy.springserver.Repository.TeamJoinRequestRepository;
import com.ssafy.springserver.Repository.TeamMemberRepository;
import com.ssafy.springserver.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamJoinRequestRepository teamJoinRequestRepository;
    // 팀 정보 조회
    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable("teamName") String teamName){
        if(teamName != null){
            Team team = teamRepository.findByTeamName(teamName);

            return team;
        }
        else return null;
    }

    // 팀원 조회
    @GetMapping("/teamMember/{teamName}")
    public List<TeamMemberDto> getTeamMember(@PathVariable("teamName") String teamName){
        if(teamName != null){
            List<TeamMember> teamMember = teamMemberRepository.findByTeamName(teamName);

            List<TeamMemberDto> teamMemberDtos = new ArrayList<>();
            for (TeamMember member: teamMember) {
                teamMemberDtos.add(new TeamMemberDto(
                        member.getTeamMemberId().getTeamName(),
                        member.getTeamMemberId().getUserId(),
                        member.getAuth()));
            }

            return teamMemberDtos;
        }
        else return null;
    }

    // 팀 요청 조회
    @GetMapping("/teamJoinRequest/{teamName}")
    public List<TeamJoinRequest> getTeamJoinRequest(@PathVariable("teamName") String teamName) {
        if (teamName != null) {
            List<TeamJoinRequest> teamJoinRequests = teamJoinRequestRepository.findByTeamName(teamName);
            return teamJoinRequests;
        }else{
            return null;
        }
    }

    // 팀 중복 조회
    @GetMapping("/duplicationCheck/{teamName}")
    public Team checkTeamName(@PathVariable("teamName") String teamName){
        if(teamName != null){
            Team team = teamRepository.findByTeamName(teamName);
            return team;
        }
        else return null;
    }

    // 팀 삭제
    @PostMapping("/teamDelete/{teamName}")
    public int deleteTeamRequest(@PathVariable("teamName") String teamName){
        if(teamName != null){
            return teamRepository.deleteTeam(teamName);
        }
        else return 0;
    }
}
