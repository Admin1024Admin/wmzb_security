package com.l024.wmzbsecuritycore.validate.code;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 验证码实体类
 */
public class SmsCode {
    private String code;
    private LocalDateTime expireTime;

    //过期时间+秒
    public SmsCode(String code, int send) {
        this.code = code;
        //当前时间+秒数
        this.expireTime = LocalDateTime.now().plusSeconds(send);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpried(){
        if(System.currentTimeMillis() >= Instant.from(this.expireTime.atZone(ZoneId.systemDefault())).toEpochMilli()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "SmsCode{" +
                "code='" + code + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
