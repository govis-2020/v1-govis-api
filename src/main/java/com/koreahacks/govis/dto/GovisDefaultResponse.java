package com.koreahacks.govis.dto;

import com.koreahacks.govis.enums.ReturnCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GovisDefaultResponse {

    private int code;
    private String message;

    public GovisDefaultResponse(ReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.message = returnCode.getMessage();
    }
}
