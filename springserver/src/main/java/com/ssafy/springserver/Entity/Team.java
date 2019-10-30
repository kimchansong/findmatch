package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "team")
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
