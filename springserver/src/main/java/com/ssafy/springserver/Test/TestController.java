package com.ssafy.springserver.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @PostMapping("/test")
    public void test(){
        testRepository.save(Test.builder().test(1).build());
    }
}
