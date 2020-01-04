package com.koreahacks.govis.dto;

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
        List<Integer> userKeywordIds;
    }
}
