package com.ssafy.springserver.Controller;

import com.ssafy.springserver.Entity.Board;
import com.ssafy.springserver.Entity.BoardDto;
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
        List<Board> boardList =  boardRepository.allBoard();
        return boardList;
    }

    @PostMapping("/board/write")
    @ResponseBody
    public Board writeBoard(@RequestBody Board item){
        System.out.println("쓰기 : " + item.toString());
        return boardRepository.save(item);
    }

    @PostMapping("/board/delete/{num}")
    @ResponseBody
    public int deleteBoard(@PathVariable int num){
        System.out.println("삭제번호 : " + num);
        boardRepository.deleteById(num);
        return 1;
    }

    @PostMapping("/board/update")
    @ResponseBody
    public int updateBoard(@RequestBody Board item){
        System.out.println("수정하기 : " + item.toString());

        Board target =  boardRepository.getOne(item.getB_num());
        target.setB_content(item.getB_content());
        target.setB_title(item.getB_title());
        target.setB_date(item.getB_date());
        target.setB_type(item.getB_type());

        boardRepository.save(target);
        return 1;
    }
}
