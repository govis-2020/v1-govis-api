package com.koreahacks.govis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GovisException extends RuntimeException {

    private int code;
    private String message;
}
