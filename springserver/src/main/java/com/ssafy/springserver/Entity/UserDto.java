package com.ssafy.springserver.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String userName;
    private String userAge;
    private String userPhone;
    private int userPoint;

}
