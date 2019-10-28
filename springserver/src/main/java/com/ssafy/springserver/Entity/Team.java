package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
@Table(name = "team")
public class Team {
    @Id
    private @Column(name = "t_name") String teamName;
    private @Column(name = "t_info") String teamInfo;
    private @Column(name = "t_locate") String teamLocate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="Team_t_name")
    private Collection<TeamMember> teamMemberCollection;


    @Builder
    Team(String teamName, String teamInfo, String teamLocate){
        this.teamName = teamName;
        this.teamInfo = teamInfo;
        this.teamLocate = teamLocate;
    }
}
