package com.ssafy.springserver.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "matching")
public class Matching {
    @Id
    @GeneratedValue
    private @Column(name = "matching_num") Long mno;
    private @Column(name = "matching_status") String status;
    private @Column(name = "matching_uid") String uid;
    private @Column(name = "home_team") String homeTeam;
}
