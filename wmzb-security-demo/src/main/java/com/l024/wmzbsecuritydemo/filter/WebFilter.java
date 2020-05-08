package com.l024.wmzbsecuritydemo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 过滤器 一般日志 不能拿到具体的视图
 */
@Component
public class WebFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器开始");
        //执行下一个过滤器
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("过滤器结束");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
