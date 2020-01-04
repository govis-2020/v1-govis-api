package com.koreahacks.govis.exception;

import com.koreahacks.govis.enums.ReturnCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GovisException extends RuntimeException {

    private int code;
    private String message;

    public GovisException(ReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.message = returnCode.getMessage();
    }
}
