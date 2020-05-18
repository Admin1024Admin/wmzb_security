package com.l024.wmzbsecuritydemo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.l024.wmzbsecuritycore.exception.FileException;
import com.l024.wmzbsecuritycore.exception.UserException;
import com.l024.wmzbsecuritycore.model.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 用户相关controller
 */
@Api(value = "用户相关controller")
@RestController
public class UserController {


    //request缓存
    private RequestCache requestCache = new HttpSessionRequestCache();
    //重定向
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("/user")
    @JsonView(User.UserSimpleView.class)
    public List<User> getUser(){
        List<User> users = new ArrayList<>();
        users.add(new User("aaa","123"));
        users.add(new User("bbb","123"));
        users.add(new User("ccc","123"));
        return users;
    }

    /**
     * 添加用户
     * RequestBody  json转为实体类
     * @param user
     */
    @ApiOperation(httpMethod = "GET",value = "添加用户",notes = "添加用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province",required = false, value = "省",paramType ="query", dataType = "String"),
            @ApiImplicitParam(name = "city",required = false, value = "市",paramType ="query", dataType = "String"),
            @ApiImplicitParam(name = "county",required = false, value = "县",paramType ="query", dataType = "String"),
            @ApiImplicitParam(name = "pageable",required = false, value = "分页，排序对象",paramType ="body", dataType = "Pageable")
    })
    @PostMapping("/user")
    public void addUser(@RequestBody User user){
        throw new UserException("124545");
    }

    /**
     * 文件上传
     */
    @PostMapping("/user/upload")
    public Callable<String> uploadFile(@RequestParam("file") MultipartFile multipartFile){
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(multipartFile!=null){
                    String name = multipartFile.getName();
                    long size = multipartFile.getSize();
                    String originalFilename = multipartFile.getOriginalFilename();
                    System.out.println("name:"+name+"size:"+size+"originalFilename:"+originalFilename);
                    File file = new File("D:\\","1.txt");
                    try {
                        multipartFile.transferTo(file);
                    } catch (IOException e) {
                        throw new FileException("保存上传文件失败");
                    }
                    return "上传成功";
                }else{
                    throw new FileException("上传文件为null");
                }
            }
        };
        return result;
    }

    /**
     * 文件下载
     */
    @GetMapping("/user/down")
    public void downFile(HttpServletResponse response) throws IOException {
        String filename = "1.txt";
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        InputStream inputStream = new FileInputStream(new File("D:/"+filename));
        IOUtils.copy(inputStream,response.getOutputStream(),1024);
    }

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
        System.out.println("跳转页面");
        if(savedRequest!=null){
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("引发跳转的url:"+targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                redirectStrategy.sendRedirect(request,response,"/login.html");
            }
        }
        redirectStrategy.sendRedirect(request,response,"/login.html");

//        return new SimpleResponse("401","访问服务需要身份认证，请引导到登录页面","http://127.0.0.1:9999/login.html");
    }

    @GetMapping("/getcode")
    @ResponseBody
    public String getCode(){
        return "xxx";
    }
}
