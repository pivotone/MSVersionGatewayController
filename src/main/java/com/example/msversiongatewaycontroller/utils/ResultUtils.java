package com.example.msversiongatewaycontroller.utils;

import com.example.msversiongatewaycontroller.entity.Result;

public class ResultUtils {
    public static Result success(Object object){
        Result result = new Result();
        result.setCode("0000");
        result.setMessage("请求成功");
        result.setData(object);
        return result;
    }

    public static Result error(String code,String message){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result success(){
        Result result = new Result();
        result.setCode("0000");
        result.setMessage("请求成功");
        return result;
    }

    public static Result success(String message, Object object){
        Result result = new Result();
        result.setCode("0000");
        result.setMessage(message);
        result.setData(object);
        return result;
    }
}
