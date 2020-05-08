package com.l024.wmzbsecurityborowser.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 访问html直接跳转到html
     * 2.访问其他请求则直接提示非法请求
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest!=null){
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("引发跳转的url:"+targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                redirectStrategy.sendRedirect(request,response,"/login.html");
            }
        }
        return "访问服务需要身份认证，请引导到登录页面";
    }
}
