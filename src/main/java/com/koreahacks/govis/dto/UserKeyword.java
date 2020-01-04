package com.koreahacks.govis.dto;

import com.koreahacks.govis.enums.ReturnCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserKeyword {

    @Getter
    @ApiModel(value = "유저의 관심있는 키워드 목록 저장 api request")
    public static class Request {

        @NotNull
        @ApiModelProperty(notes = "키워드 목록")
        List<String> userKeywords;
    }


    @Getter
    @ApiModel(value = "유저가 지정한 관심있는 키워드 목록 조회 response")
    public static class Response extends GovisDefaultResponse {

        @ApiModelProperty(notes = "키워드 목록")
        List<String> userKeywords;

        public Response(int code, String message) {
            super(code, message);
        }

        public Response(ReturnCode returnCode, List<String> userKeywords) {
            super(returnCode);
            this.userKeywords = userKeywords;
        }
    }
}
