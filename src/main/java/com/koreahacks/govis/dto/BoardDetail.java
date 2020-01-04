package com.koreahacks.govis.dto;

import com.koreahacks.govis.entity.board.BoardEntity;
import com.koreahacks.govis.enums.ReturnCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class BoardDetail {

    @Getter
    @ApiModel(value = "게시물 상세 response")
    public static class Response extends GovisDefaultResponse {

        @ApiModelProperty(notes = "게시물 상세 정보")
        private BoardEntity crawlData;

        public Response(int code, String message) {
            super(code, message);
        }

        public Response(ReturnCode returnCode, BoardEntity crawlData) {
            super(returnCode);
            this.crawlData = crawlData;
        }
    }
}
