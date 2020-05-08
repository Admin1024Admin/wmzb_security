package com.l024.wmzbsecuritycore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常
 */
@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(UserException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserException(UserException user){
        HashMap hashMap = new HashMap();
        hashMap.put("msg","用户不存在");
        return hashMap;
    }
}
