package com.koreahacks.govis.controller.board;

import com.koreahacks.govis.dto.Board;
import com.koreahacks.govis.dto.BoardDetail;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.service.board.BoardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("boards")
    @ApiOperation(httpMethod = "GET", value = "게시물 리스트 조회")
    public Board.Response getBoards(@RequestHeader("userId") String userId,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    @RequestParam(value = "offset", required = false) Integer offset) throws Exception {

        try {
            if ("".equals(userId)) {
                throw new GovisException(ReturnCode.UNAUTHORIZED);
            }

            if (limit == null) {
                limit = 10;
            }
            if (offset == null) {
                offset = 0;
            }

            return new Board.Response(ReturnCode.SUCCESS, boardService.getBoards(limit, offset));

        } catch (GovisException e) {
            return new Board.Response(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("board/{boardId}")
    @ApiOperation(httpMethod = "GET", value = "게시물 상세 조회")
    public BoardDetail.Response getBoardDetail(@RequestHeader("userId") String userId,
                                               @PathVariable(value = "boardId") String boardId) throws Exception {

        try {
            if ("".equals(userId)) {
                throw new GovisException(ReturnCode.UNAUTHORIZED);
            }
            if ("".equals(boardId)) {
                throw new GovisException(ReturnCode.BAD_REQUEST);
            }

            return new BoardDetail.Response(ReturnCode.SUCCESS, boardService.getBoardDetail(Integer.parseInt(boardId)));

        } catch (GovisException e) {
            return new BoardDetail.Response(e.getCode(), e.getMessage());
        }
    }
}
