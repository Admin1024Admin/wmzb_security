package com.l024.wmzbsecuritycore.authentication.moblie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 认证具体实现  校验逻辑
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //用户信息
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 核心校验
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //强转获取用户认证信息
        SmsCodeAuthenticationToken authenticationToken =(SmsCodeAuthenticationToken) authentication;
        //获取用户的相关信息 得到手机号
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user==null){
            throw new InternalAuthenticationServiceException("无法读取用户信息");
        }
        //得到认证完毕后的数据
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
        //将传递的authenticationToken中的Details赋值到返回的authenticationToken中
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * 让AuthenticationManager可以通过调用该方法
     * 辨别当前AuthenticationProvider是否是完成相应验证工作的supports方法：
     * 判断AuthenticationToken是否是SmsCodeAuthenticationToken.class
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsCodeAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
