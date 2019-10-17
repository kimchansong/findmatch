package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.Matching;
import com.ssafy.springserver.Repository.MatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    MatchingRepository matchingRepository;

    @PostMapping("match/insert")
    @ResponseBody
    public void insertMatch(@RequestBody Matching match) {
        try {
            matchingRepository.save(match);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
