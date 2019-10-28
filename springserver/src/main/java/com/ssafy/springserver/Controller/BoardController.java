package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.Board;
import com.ssafy.springserver.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/board/write")
    @ResponseBody
    public Board writeBoard(@RequestBody Board item){
        return boardRepository.save(item);
    }

}
