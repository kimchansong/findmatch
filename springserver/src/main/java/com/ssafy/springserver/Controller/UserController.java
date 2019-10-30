package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.User;
import com.ssafy.springserver.Entity.UserDto;
import com.ssafy.springserver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public User checkUser(@PathVariable("userId") String userId){
        if(userId != null){
            User user = userRepository.checkId(userId);
            if(user != null) return user;
            else return null;
        }else
            return null;
    }

    @PostMapping("/user/insert")
    public int insertUser(@RequestBody UserDto userDto) {
        userRepository.save(User.builder()
                .userId(userDto.getUserId())
                .userName(userDto.getUserName())
                .userAge(userDto.getUserAge())
                .userPhone(userDto.getUserPhone())
                .userPoint(userDto.getUserPoint())
                .build());
        return 1;
    }

    @PostMapping("/user/update")
    @ResponseBody
    public int updateBoard(@RequestBody UserDto user){

        User target =  userRepository.getOne(user.getUserId());
        target.setUserName(user.getUserName());
        target.setUserAge(user.getUserAge());
        target.setUserPhone(user.getUserPhone());
        target.setUserPoint(user.getUserPoint());

        userRepository.save(target);
        return 1;
    }


}