package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.User;
import com.ssafy.springserver.Entity.UserDto;
import com.ssafy.springserver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"})
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public User checkUser(@PathVariable("userId") String userId){
        System.out.println(userId);
        if(userId != null){
            User user = userRepository.checkId(userId);
            return user;
        }else
            return null;
    }
}