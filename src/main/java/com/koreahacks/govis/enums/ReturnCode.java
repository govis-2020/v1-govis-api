package com.koreahacks.govis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {

    SUCCESS(200,"Success"),
    UNAUTHORIZED(401, "Unauthorized"),
    NO_DATA_IN_GOOGLE(1000, "No user data in Google"),
    NO_KEYWORD(1001, "No keyword data");

    private int code;
    private String message;
}
