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
    private @Column(name = "t_name") String teamName;
    private @Column(name = "t_info") String teamInfo;
    private @Column(name = "t_locate") String teamLocate;

    @Builder
    Team(String teamName, String teamInfo, String teamLocate){
        this.teamName = teamName;
        this.teamInfo = teamInfo;
        this.teamLocate = teamLocate;
    }
}
