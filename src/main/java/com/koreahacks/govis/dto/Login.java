package com.koreahacks.govis.dto;

import com.koreahacks.govis.enums.ReturnCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class Login {

    @Getter
    @AllArgsConstructor
    @ApiModel(value = "로그인 요청 request")
    public static class Request {

        @NotBlank
        @ApiModelProperty(notes = "구글 로그인 성공 시 주어지는 id token")
        private String googleIdToken;
    }

    @Getter
    @ApiModel(value = "로그인 요청 response")
    public static class Response extends GovisDefaultResponse {

        @ApiModelProperty(notes = "고비스 로그인 성공 시 내려가는 로그인 정보")
        private Login.Info loginInfo;

        public Response(int code, String message) {
            super(code, message);
        }

        public Response(ReturnCode returnCode, Login.Info loginInfo) {
            super(returnCode);
            this.loginInfo = loginInfo;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @ApiModel(value = "로그인 정보")
    public static class Info {

        @ApiModelProperty(notes = "고비스 accss token")
        private String accessToken;
        @ApiModelProperty(notes = "유저 정보")
        private User.Info userInfo;
    }
}
