package com.l024.wmzbsecuritycore.social.configUtils;

import com.l024.wmzbsecuritycore.properties.QQProperties;
import com.l024.wmzbsecuritycore.properties.SecurityProperties;
import com.l024.wmzbsecuritycore.social.qq.connet.QQConnetionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

@Configuration
//表示配置中配置了appid这个配置类才起作用
//@ConditionalOnProperty(prefix = "wmzb.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter{
    @Autowired
    private SecurityProperties securityProperties;
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qq = securityProperties.getSocial().getQq();
        return new QQConnetionFactory("qq","101835221","616a6fd0b6fde82b0d723b5b74667fcb");
    }
}
