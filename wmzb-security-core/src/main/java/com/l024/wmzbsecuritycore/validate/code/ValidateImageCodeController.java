package com.l024.wmzbsecuritycore.validate.code;


import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public static final String SESSION_SMS_CODE_KEY = "SESSION_KEY_MOBILE_CODE";
    //spring-session创建session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建图形验证码
        ImageCode imageCode = new ValidateCodeGenerator().generateCodeAndPic();
        ImageCode code = new ImageCode(imageCode.getCode(),imageCode.getExpireTime());
        //存入session
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,code);
        //将图片返回
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    @GetMapping("/code/sms")
    @ResponseBody
    public SmsCode createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建图形验证码
        SmsCode smsCode = new ValidateCodeGenerator().generateSmsCode();
        //存入session
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_SMS_CODE_KEY,smsCode);
        //打印验证码
        System.out.println("打印数--"+smsCode);
        return smsCode;
    }
}
