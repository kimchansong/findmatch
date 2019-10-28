package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.Board;
import com.ssafy.springserver.Entity.BoardDto;
import com.ssafy.springserver.Entity.Matching;
import com.ssafy.springserver.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
