package com.example.msversiongatewaycontroller.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private String code;
    private String message;
    private Object data;

    public String toString(){
        return "Message{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ",data='" + data +'\'' + "}";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}