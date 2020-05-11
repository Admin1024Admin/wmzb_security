package com.l024.wmzbsecuritycore.support;

import java.io.Serializable;

/**
 * Response数据返回实体类
 */
public class SimpleResponse implements Serializable {

    private String code;

    private String msg;

    private Object data;

    public SimpleResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SimpleResponse(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
