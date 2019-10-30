package com.ssafy.springserver.Entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class TeamMemberId implements Serializable {
    private String teamName;
    private String userId;
}
