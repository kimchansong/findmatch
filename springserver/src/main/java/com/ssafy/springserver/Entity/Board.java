package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private @Column(name = "b_num") int b_num;
    private @Column(name = "User_u_id") String User_u_id;
    private @Column(name = "b_title") String b_title;
    private @Column(name = "b_content") String b_content;
    private @Column(name = "b_type") int b_type;
    private @Column(name = "b_date") String b_date;


    @Builder
    Board(int b_num, String User_u_id, String b_title, String b_content, int b_type , String b_date){
        this.b_num = b_num;
        this.User_u_id = User_u_id;
        this.b_title = b_title;
        this.b_content = b_content;
        this.b_type = b_type;
        this.b_date = b_date;
    }
}
