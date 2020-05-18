package com.l024.wmzbsecuritycore.properties;

import com.l024.wmzbsecuritycore.social.configUtils.SocialProperties;

/**
 * QQ登录配置
 */
public class QQProperties extends SocialProperties {
    //服务商id
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
