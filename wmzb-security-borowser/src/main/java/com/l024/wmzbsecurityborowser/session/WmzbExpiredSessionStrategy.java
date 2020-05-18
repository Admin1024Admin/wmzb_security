package com.l024.wmzbsecurityborowser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session过期后处理
 */
public class WmzbExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        System.out.println("并发登录");
        event.getResponse().setContentType("application/json:charset=utf-8");
        event.getResponse().getWriter().write("你的账号在其他地方登录");
    }
}
