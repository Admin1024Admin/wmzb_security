package com.l024.wmzbsecuritycore.validate.code;

import com.l024.wmzbsecuritycore.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器  OncePerRequestFilter保证过滤器只调用一次
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //验证失败处理handler
    private AuthenticationFailureHandler authenticationFailureHandler;
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * 1.只有在登录的时候才进行过滤
         * 2.从session取出验证码与用户传递比较
         *3.
         */
        if(StringUtils.equalsIgnoreCase("/authentication/from",request.getRequestURI())
            &&StringUtils.equalsIgnoreCase(request.getMethod(),"POST")){
            try {
                validate(new ServletWebRequest(request));
            }catch (ValidateCodeException e){
                //校验失败直接调用security登录失败的handler
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        //其他请求直接放行
        filterChain.doFilter(request,response);
    }

    /**
     * 校验验证码
     * @param request
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(request,ValidateImageCodeController.SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");
        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不可为空");
        }
        if(codeInSession==null){
            throw new ValidateCodeException("验证码不存在");
        }
        if(codeInSession.isExpried()){
            throw new ValidateCodeException("验证码已过期");
        }
        if(!StringUtils.equalsIgnoreCase(codeInSession.getCode(),codeInRequest)){
            throw new ValidateCodeException("验证码输入错误");
        }
        //移除验证码
        sessionStrategy.removeAttribute(request,ValidateImageCodeController.SESSION_KEY);
    }
}
