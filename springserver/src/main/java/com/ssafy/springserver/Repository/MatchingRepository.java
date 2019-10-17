package com.ssafy.springserver.Repository;

import com.ssafy.springserver.Entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

}
