package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Team")
public class Team {
    @Id
    private @Column(name = "team_name") String teamName;
    private @Column(name = "team_info") String teamInfo;

    @Builder
    Team(String teamName, String teamInfo){
        this.teamName = teamName;
        this.teamInfo = teamInfo;
    }
}
