package com.finalproject.chorok.login.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

//    INVALID_PARAMETER(400, null, "Invalid Request Data"),
//    EXPIRED_TOKEN(410, "C001", "Token Was Expired");
    TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "U004","만료된 액세스 토큰 입니다."),
    DONT_USE_THIS_TOKEN(HttpStatus.BAD_REQUEST.value(), "U006","유효하지 않은 토큰 입니다.");
//    COUPON_NOT_FOUND(404, "C002", "Coupon Not Found");


    private final int httpsStatus;
    private final String code;
    private final String message;


    }
