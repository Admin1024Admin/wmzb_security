package com.l024.wmzbsecuritycore.properties;

import com.l024.wmzbsecuritycore.validate.code.ValidateCodeFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * security相关配置 读取配置类中的 wmzb.security注入
 */
@ConfigurationProperties(prefix = "wmzb.security")
public class SecurityProperties {

    BrowserProperties browser = new BrowserProperties();
    ValidateCodeProperties code = new ValidateCodeProperties();

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
