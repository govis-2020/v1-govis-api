package com.koreahacks.govis.dto;

import com.koreahacks.govis.enums.ReturnCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

public class Board {

    @Data
    @ApiModel(value = "리스트에 표현될 게시물 간이 정보")
    public static class Info {

        @ApiModelProperty(notes = "게시물 id")
        private long id;

        @ApiModelProperty(notes = "제목")
        private String title;

        @ApiModelProperty(notes = "관심사")
        private String keyword;

        @ApiModelProperty(notes = "게시일")
        private Date createdAt;
    }

    @Getter
    @ApiModel(value = "게시물 리스트 조회 response")
    public static class Response extends GovisDefaultResponse {

        @ApiModelProperty(notes = "게시물 리스트")
        List<Info> infos;

        public Response(int code, String message) {
            super(code, message);
        }

        public Response(ReturnCode returnCode, List<Board.Info> infos) {
            super(returnCode);
            this.infos = infos;
        }
    }
}
