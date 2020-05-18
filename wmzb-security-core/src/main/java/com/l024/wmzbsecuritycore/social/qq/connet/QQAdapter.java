package com.l024.wmzbsecuritycore.social.qq.connet;

import com.l024.wmzbsecuritycore.social.qq.api.QQ;
import com.l024.wmzbsecuritycore.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 适配器
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * 
     * @param qq
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo userInfo = qq.getUserInfo();
        connectionValues.setDisplayName(userInfo.getNickname()); //网名
        connectionValues.setImageUrl(userInfo.getFigureurl_1()); //qq头像
        connectionValues.setProfileUrl(null); //个人主页
        connectionValues.setProviderUserId(userInfo.getOpenId());//服务商的用户id 也就是openId
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
