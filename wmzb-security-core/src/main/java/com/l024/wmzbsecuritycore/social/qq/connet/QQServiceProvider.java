package com.l024.wmzbsecuritycore.social.qq.connet;

import com.l024.wmzbsecuritycore.social.qq.api.QQ;
import com.l024.wmzbsecuritycore.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;
    private static String authorizeUrl = "https://graph.qq.com/oauth2.0/authorize";//用户导向的服务器地址 用户在这个也页面授权
    private static String accessTokenUrl = "https://graph.qq.com/oauth2.0/token"; //获取令牌的地址

    public QQServiceProvider(String appId,String appSecret) {
        //appid appSecret 用户导向的服务器地址
        super(new OAuth2Template(appId,appSecret,authorizeUrl,accessTokenUrl));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
