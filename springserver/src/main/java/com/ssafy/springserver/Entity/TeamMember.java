package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Team_has_User")
@IdClass(TeamMemberId.class)
public class TeamMember {
    @Id
    @Column(name = "Team_t_name")
    private String teamName;

    @Id
    @Column(name = "User_u_id")
    private String userId;

    @Column(name = "t_auth")
    private  String auth;

    @Builder
    TeamMember(String teamName, String userId, String auth){
        this.teamName = teamName;
        this.userId = userId;
        this.auth = auth;
    }
}
