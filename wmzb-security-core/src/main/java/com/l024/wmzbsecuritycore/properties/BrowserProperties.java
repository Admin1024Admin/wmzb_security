package com.l024.wmzbsecuritycore.properties;

import sun.dc.pr.PRError;

/**
 * 浏览器安全相关配置类
 */
public class BrowserProperties {
    private String loginPage = "/login.html";

    private LoginType loginType = LoginType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
