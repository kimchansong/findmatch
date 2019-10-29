package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String>{
    @Query("SELECT user FROM User user WHERE u_id = :userId")
    User checkId(@Param("userId") String userId);

}