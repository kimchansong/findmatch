package com.ssafy.springserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
@Table(name = "User")
public class User {
    @Id
    private @Column(name = "u_id") String userId;
    private @Column(name = "u_name") String userName;
    private @Column(name = "u_age") int userAge;
    private @Column(name = "u_phone") String userPhone;
    private @Column(name = "u_point") int userPoint;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="Team_t_name")
    private Collection<TeamMember> teamMemberCollection;

    @Builder
    User(String userId, String userName, int userAge, String userPhone, int userPoint){
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.userPoint = userPoint;
    }

}
