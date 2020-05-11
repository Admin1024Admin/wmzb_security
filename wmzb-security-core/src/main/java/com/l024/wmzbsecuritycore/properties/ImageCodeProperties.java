package com.l024.wmzbsecuritycore.properties;

/**
 * 验证码配置(应用级配置)
 */
public class ImageCodeProperties {
    private int width = 67;
    private int height = 23;
    private int length = 4;
    private int expire = 60;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
