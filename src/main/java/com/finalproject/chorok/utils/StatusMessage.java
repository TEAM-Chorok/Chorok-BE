package com.finalproject.chorok.utils;

import lombok.Data;

@Data
public class StatusMessage {

    private StatusEnum statusCode;
    private String message;
    private Object data;

    public StatusMessage(){

        this.statusCode = StatusEnum.BAD_REQUEST;
        this.message = null;
    }


    public enum StatusEnum{
        OK(200, "OK"),
        BAD_REQUEST(400, "BAD_REQUEST"),
        NOT_FOUND(404, "NOT_FOUND"),
        INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

        int statusCode;
        String code;

        StatusEnum(int statusCode, String code) {
            this.statusCode = statusCode;
            this.code = code;
        }


    }
}
