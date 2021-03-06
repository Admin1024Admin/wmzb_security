package com.l024.wmzbsecuritycore.validate.code;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 图形验证码实体类
 */
public class ImageCode implements Serializable {
    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime;

    //过期时间+秒
    public ImageCode(BufferedImage image, String code, int send) {
        this.image = image;
        this.code = code;
        //当前时间+秒数
        this.expireTime = LocalDateTime.now().plusSeconds(send);
    }

    //过期时间+秒
    public ImageCode(String code, LocalDateTime localDateTime) {
        this.code = code;
        //当前时间+秒数
        this.expireTime = localDateTime;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
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
}
