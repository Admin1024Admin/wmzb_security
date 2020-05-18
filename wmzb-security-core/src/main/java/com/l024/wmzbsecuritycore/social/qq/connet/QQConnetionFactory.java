package com.l024.wmzbsecuritycore.social.qq.connet;

import com.l024.wmzbsecuritycore.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQConnetionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnetionFactory(String providerId,String appId,String appSecret) {
        //服务商id
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
