package com.koreahacks.govis.controller.board;

import com.koreahacks.govis.dto.Board;
import com.koreahacks.govis.dto.BoardDetail;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.service.board.BoardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("boards")
    @ApiOperation(httpMethod = "GET", value = "게시물 리스트 조회")
    public Board.Response getBoards(@RequestParam(value = "limit", required = false) Integer limit,
                                    @RequestParam(value = "offset", required = false) Integer offset,
                                    @ApiParam(value = "Y로 보내줄 시 관심사 게시물 조회")
                                    @RequestParam(value = "interest", required = false) String interest,
                                    HttpServletRequest request) throws Exception {

        try {
            String userId = (String) request.getAttribute("userId");

            if ("".equals(userId)) {
                throw new GovisException(ReturnCode.UNAUTHORIZED);
            }

            if (limit == null) {
                limit = 10;
            }
            if (offset == null) {
                offset = 0;
            }
            if ("Y".equals(interest)) {
                return new Board.Response(ReturnCode.SUCCESS, boardService.getInterestBoards(Integer.parseInt(userId),
                        limit, offset));
            }

            return new Board.Response(ReturnCode.SUCCESS, boardService.getBoards(limit, offset));

        } catch (GovisException e) {
            return new Board.Response(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("board/{boardId}")
    @ApiOperation(httpMethod = "GET", value = "게시물 상세 조회")
    public BoardDetail.Response getBoardDetail(@PathVariable(value = "boardId") String boardId,
                                               HttpServletRequest request) throws Exception {

        try {
            String userId = (String) request.getAttribute("userId");

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
