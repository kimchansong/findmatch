package com.ssafy.springserver.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamMemberDto {
    private String teamName;
    private String userId;
    private String auth;
}
