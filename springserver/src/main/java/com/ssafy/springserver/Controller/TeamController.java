package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.Team;
import com.ssafy.springserver.Entity.TeamDto;
import com.ssafy.springserver.Entity.TeamMember;
import com.ssafy.springserver.Entity.TeamMemberDto;
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

    // 팀 정보 조회
    @GetMapping("/team/{teamName}")
    public TeamDto getTeam(@PathVariable("teamName") String teamName){
        if(teamName != null){
            Team team = teamRepository.findByName(teamName);

            return new TeamDto(team.getTeamName(), team.getTeamInfo());
        }
        else return null;
    }

    // 팀원 조회
    @GetMapping("/teamMember/{teamName}")
    public List<TeamMemberDto> getTeamMember(@PathVariable("teamName") String teamName){
        if(teamName != null){
            List<TeamMember> teamMember = teamMemberRepository.findByName(teamName);

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
    public

    @GetMapping("/duplicationCheck/{teamName}")
    public TeamDto checkTeamName(@PathVariable("teamName") String teamName){
        if(teamName != null){
            System.out.println(teamName);
            Team team = teamRepository.findByName(teamName);
            return new TeamDto(team.getTeamName(), team.getTeamInfo());
        }
        else return null;
    }

}
