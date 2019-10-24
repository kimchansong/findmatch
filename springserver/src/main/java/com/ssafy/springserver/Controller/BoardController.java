package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.*;
import com.ssafy.springserver.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    // 팀 정보 조회
    @GetMapping("/board/all")
    public List<Board> getBoard(){
        List<Board> boardList =  boardRepository.findAll();
        return boardList;
    }

}
