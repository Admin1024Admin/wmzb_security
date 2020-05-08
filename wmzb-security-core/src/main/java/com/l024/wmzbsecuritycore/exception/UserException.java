package com.l024.wmzbsecuritycore.exception;

public class UserException extends RuntimeException {

    private String id;
    public UserException(String id){
        super("用户不存在");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
