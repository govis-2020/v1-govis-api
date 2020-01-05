package com.koreahacks.govis.dao.board;

import com.koreahacks.govis.entity.board.BoardEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BoardDao {

    @Autowired
    private SqlSession sqlSession;

    public Optional<List<BoardEntity>> getBoardsWithLimitOffset(int limit, int offset) throws Exception {

        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("limit", limit);
        dataMap.put("offset", offset);

        return Optional.ofNullable(sqlSession.selectList("getBoardsWithLimitOffset", dataMap));
    }

    public Optional<List<BoardEntity>> getInterestBoardsWithLimitOffset(int userId, int limit, int offset)
            throws Exception {

        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("userId", userId);
        dataMap.put("limit", limit);
        dataMap.put("offset", offset);

        return Optional.ofNullable(sqlSession.selectList("getInterestBoardsWithLimitOffset", dataMap));
    }

    public Optional<List<BoardEntity>> getBoardsByTypeWithLimitOffset(String type, int limit, int offset)
            throws Exception {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", type);
        dataMap.put("limit", limit);
        dataMap.put("offset", offset);

        return Optional.ofNullable(sqlSession.selectList("getBoardsByTypeWithLimitOffset", dataMap));
    }
}
