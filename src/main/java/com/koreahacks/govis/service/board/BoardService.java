package com.koreahacks.govis.service.board;

import com.koreahacks.govis.dto.Board;
import com.koreahacks.govis.entity.board.BoardEntity;

import java.util.List;

public interface BoardService {

    BoardEntity getBoardDetail(int boardId) throws Exception;
    List<Board.Info> getBoards(int limit, int offset) throws Exception;
    List<Board.Info> getInterestBoards(int userId, int limit, int offset) throws Exception;
    List<Board.Info> getBoardsByType(String type, int limit, int offset) throws Exception;
}
