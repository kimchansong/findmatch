package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.Team;
import com.ssafy.springserver.Entity.TeamDto;
import com.ssafy.springserver.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    // 팀 정보 조회
    @GetMapping("/team/{teamName}")
    public TeamDto getTeam(@PathVariable("teamName") String teamName){
        if(teamName != null){
            Team team = teamRepository.findByName(teamName);

            return new TeamDto(team.getTeamName(), team.getTeamInfo());
        }
        else return null;
    }
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
