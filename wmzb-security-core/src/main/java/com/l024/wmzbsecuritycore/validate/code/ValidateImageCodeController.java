package com.l024.wmzbsecuritycore.validate.code;

import com.sun.imageio.plugins.common.ImageUtil;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图形验证
 */
@Controller
public class ValidateImageCodeController {
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    //spring-session创建session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建图形验证码
        ImageCode imageCode = new CodeUtil().generateCodeAndPic();
        //存入session
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        //将图片返回
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }
}
