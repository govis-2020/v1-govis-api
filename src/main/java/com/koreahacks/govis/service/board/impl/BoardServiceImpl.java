package com.koreahacks.govis.service.board.impl;

import com.koreahacks.govis.dao.board.BoardDao;
import com.koreahacks.govis.dto.Board;
import com.koreahacks.govis.entity.board.BoardEntity;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.repository.board.BoardRepository;
import com.koreahacks.govis.service.board.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardDao boardDao;

    @Override
    public BoardEntity getBoardDetail(int boardId) throws Exception {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new GovisException(ReturnCode.NO_BOARD));
    }

    @Override
    public List<Board.Info> getBoards(int limit, int offset) throws Exception {

        List<BoardEntity> boardEntities = boardDao.getBoardsWithLimitOffset(limit, offset)
                .orElseThrow(() -> new GovisException(ReturnCode.NO_BOARD));

        return boardEntities.stream()
                .map(entity -> modelMapper.map(entity, Board.Info.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Board.Info> getInterestBoards(int userId, int limit, int offset) throws Exception {

        List<BoardEntity> boardEntities = boardDao.getInterestBoardsWithLimitOffset(userId, limit, offset)
                .orElseThrow(() -> new GovisException(ReturnCode.NO_BOARD));

        return boardEntities.stream()
                .map(entity -> modelMapper.map(entity, Board.Info.class))
                .collect(Collectors.toList());
    }
}
