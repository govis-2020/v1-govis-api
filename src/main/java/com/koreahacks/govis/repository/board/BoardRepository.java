package com.koreahacks.govis.repository.board;

import com.koreahacks.govis.entity.board.BoardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<BoardEntity, Integer> {

}
