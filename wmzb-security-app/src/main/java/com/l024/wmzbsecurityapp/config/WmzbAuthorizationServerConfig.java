package com.l024.wmzbsecurityapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class WmzbAuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {
    /**
     * {
     *     "timestamp": 1589352742219,
     *     "status": 500,
     *     "error": "Internal Server Error",
     *     "message": "At least one redirect_uri must be registered with the client.",
     *     "path": "/oauth/authorize"
     * }
     * 报错解决
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //添加客户端信息
        clients.inMemory()                  // 使用in-memory存储客户端信息
                .withClient("wmzb")//client_id
                .authorizedGrantTypes("authorization_code")//授权码模式 必须设置
                .redirectUris("http://example.com");
    }
}
