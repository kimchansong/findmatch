package com.ssafy.springserver.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Team_Join")
public class TeamJoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "team_join_request_number") Long number;
    private @Column(name = "Team_t_name") String teamName;
    private @Column(name = "User_u_id") String userId;
    private @Column(name = "status") String status;
}
