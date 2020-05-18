package com.l024.wmzbsecuritycore.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * security相关配置 读取配置类中的 wmzb.security注入
 * 我们为属性配置错误的值时，而又不希望 Spring Boot 应用启动失败，
 * 我们可以设置 ignoreInvalidFields 属性为 true
 */
@ConfigurationProperties(prefix = "wmzb.security",ignoreInvalidFields = true)
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();
    private ValidateCodeProperties code = new ValidateCodeProperties();
    private SocialProperties social = new SocialProperties();

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
