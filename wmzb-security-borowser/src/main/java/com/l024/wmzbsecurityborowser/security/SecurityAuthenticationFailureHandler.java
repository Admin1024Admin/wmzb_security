package com.l024.wmzbsecurityborowser.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.l024.wmzbsecurityborowser.support.SimpleResponse;
import com.l024.wmzbsecuritycore.properties.LoginType;
import com.l024.wmzbsecuritycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败处理 AuthenticationFailureHandler
 */
@Component
public class SecurityAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println("登录失败处理"+securityProperties.getBrowser().getLoginType());
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("401",e.getMessage())));
        }else{
            super.onAuthenticationFailure(request,response,e);
        }
    }
}
