package com.ssafy.springserver.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDto {
    private int b_num;
    private String User_u_id;
    private String b_title;
    private String b_content;
    private int b_type;
    private String b_date;
}
