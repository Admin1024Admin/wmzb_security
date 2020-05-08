package com.l024.wmzbsecuritydemo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器  不能拿到具体的传递参数
 */
@Component
public class WebInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(WebInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
        System.out.println("preHandle开始");
        logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(),request.getRequestURI());
        //从请求头中获取用户token（登陆凭证根据业务而定）
        Long userId= getUserId(request.getHeader("H-User-Token"));
        if (userId != null && checkAuth(userId,request.getRequestURI())){
            return true;
        }
        return true;
    }

    private boolean checkAuth(Long userId, String requestURI) {
        return true;
    }

    private Long getUserId(String header) {
        return 0L;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //在业务处理器处理请求执行完成后，生成视图之前执行。后处理
        System.out.println("postHandle处理完毕");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理
    }
}
