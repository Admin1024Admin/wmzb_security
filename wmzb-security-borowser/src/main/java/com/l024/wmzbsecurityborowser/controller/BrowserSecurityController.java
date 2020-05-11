package com.l024.wmzbsecurityborowser.controller;

import com.l024.wmzbsecuritycore.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

//    @Value("${wmzb.security.browser.loginPage}")
//    private String loginPage;

    //request缓存
    private RequestCache requestCache = new HttpSessionRequestCache();
    //重定向
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;
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
    public void authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest!=null){
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("引发跳转的url:"+targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());

//        return new SimpleResponse("401","访问服务需要身份认证，请引导到登录页面","http://127.0.0.1:9999/login.html");
    }

    @PostMapping("/login/success")
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest!=null){
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("引发跳转的url:"+targetUrl);
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }
    }

    /**
     * 短信验证码登录
     */
    @PostMapping("/authentication/mobile")
    public void smsLogin(){
        System.out.println("手机验证码登录.....");
    }
}
