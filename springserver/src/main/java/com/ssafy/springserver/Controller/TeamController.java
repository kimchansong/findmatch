package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.*;
import com.ssafy.springserver.Repository.TeamJoinRequestRepository;
import com.ssafy.springserver.Repository.TeamMemberRepository;
import com.ssafy.springserver.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
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

    // 내팀 조회
    @GetMapping("/myTeam/{userId}")
    public List<TeamMemberDto> getMyTeam(@PathVariable("userId") String userId){
        if(userId != null){
            List<TeamMember> teamMember = teamMemberRepository.findByUserId(userId);

            List<TeamMemberDto> teamMemberDtos = new ArrayList<>();
            for (TeamMember member: teamMember) {
                teamMemberDtos.add(new TeamMemberDto(
                        member.getTeamMemberId().getTeamName(),
                        member.getTeamMemberId().getUserId(),
                        member.getAuth()));
            }

            return teamMemberDtos;
        }
        return null;
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
    public int checkTeamName(@PathVariable("teamName") String teamName){
        if(teamName != null){
            if(teamRepository.findByTeamName(teamName) != null) return 1;
            return 0;
        }
        else return 0;
    }

    // 팀 삭제
    @PostMapping("/team/delete/{teamName}")
    public int deleteTeamRequest(@PathVariable("teamName") String teamName){
        System.out.println(teamName);
        if(teamName != null){
            teamRepository.deleteById(teamName);
            return 1;
        }
        else return 0;
    }

    // 팀 생성
    @PostMapping("/team/add")
    public int addTeam(@RequestBody TeamDto team){
        teamRepository.save(Team.builder()
                .teamName(team.getTeamName())
                .teamInfo(team.getTeamInfo())
                .teamLocate(team.getTeamLocate()).build());

        return 0;
    }

    // 팀원 추가
    @PostMapping("/teamMember/add/{teamName}")
    public int addTeamMember(@RequestBody List<String> teamMember, @PathVariable("teamName") String teamName){
        for(String member : teamMember){
            teamMemberRepository.save(TeamMember.builder()
                    .teamName(teamName)
                    .userId(member)
                    .auth("팀원")
                    .build());
        }

        return 0;
    }
}
