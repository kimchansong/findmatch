package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Team_has_User")
public class TeamMember {

    @EmbeddedId
    private TeamMemberId teamMemberId;
    private @Column(name = "t_auth") String auth;

    @Builder
    TeamMember(String teamName, String userId, String auth){
        teamMemberId = new TeamMemberId();
        teamMemberId.setTeamName(teamName);
        teamMemberId.setUserId(userId);
        this.auth = auth;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "t_name")
    private Team team;
}
