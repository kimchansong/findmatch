package com.ssafy.springserver.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class TeamMemberId implements Serializable {
    @Column(name = "Team_t_name")
    private String teamName;

    @Column(name = "User_u_id")
    private String userId;
}
