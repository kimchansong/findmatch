package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "User")
public class User {
    @Id
    private @Column(name = "user_id") String userId;
    private @Column(name = "team_name") String userName;
    private @Column(name = "team_age") String userAge;
    private @Column(name = "team_phone") String userPhone;

    @Builder
    User(String userId, String userName, String userAge, String userPhone){
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
    }

}
