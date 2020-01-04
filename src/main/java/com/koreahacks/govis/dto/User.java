package com.koreahacks.govis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class User {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SignUp {

        private String email;
        private String userName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "로그인 성공 시 내려가는 유저 정보")
    public static class Info {

        @ApiModelProperty(notes = "유저명")
        private String userName;
        @ApiModelProperty(notes = "이메일")
        private String email;
        @ApiModelProperty(notes = "포털 인증 여부")
        private boolean isValid;
    }
}
