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
    private @Column(name = "u_id") String userId;
    private @Column(name = "u_name") String userName;
    private @Column(name = "u_age") String userAge;
    private @Column(name = "u_phone") String userPhone;
    private @Column(name = "u_point") int userPoint;

    @Builder
    User(String userId, String userName, String userAge, String userPhone, int userPoint){
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.userPoint = userPoint;
    }

}
