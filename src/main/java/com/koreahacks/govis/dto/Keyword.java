package com.koreahacks.govis.dto;

import com.koreahacks.govis.entity.keyword.KeywordEntity;
import com.koreahacks.govis.enums.ReturnCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

public class Keyword {

    @Getter
    @ApiModel(value = "현행화 되어있는 키워드 목록 조회 response")
    public static class Response extends GovisDefaultResponse {

        @ApiModelProperty(notes = "키워드 목록")
        private List<KeywordEntity> keywordEntities;

        public Response(int code, String message) {
            super(code, message);
        }

        public Response(ReturnCode returnCode, List<KeywordEntity> keywordEntities) {
            super(returnCode);
            this.keywordEntities = keywordEntities;
        }
    }
}
