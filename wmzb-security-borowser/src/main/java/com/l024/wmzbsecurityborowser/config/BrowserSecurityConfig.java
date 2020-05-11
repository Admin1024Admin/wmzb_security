package com.l024.wmzbsecurityborowser.config;

import com.l024.wmzbsecurityborowser.security.MyUserDetailsService;
import com.l024.wmzbsecurityborowser.security.SecurityAuthenticationFailureHandler;
import com.l024.wmzbsecurityborowser.security.SecurityAuthenticationSuccessHandler;
import com.l024.wmzbsecuritycore.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security配置类
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private SecurityAuthenticationSuccessHandler successHandler;

    @Autowired
    private SecurityAuthenticationFailureHandler failureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();

    /**
     * 密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 默认配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置拦截器中失败的handler
        validateCodeFilter.setAuthenticationFailureHandler(failureHandler);
        http
             //自定义过滤器 在UsernamePasswordAuthenticationFilter之前执行，
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()// 定义当需要用户登录时候，转到的登录页面。
            .loginPage("/authentication/require")//自定义登录
            .loginProcessingUrl("/authentication/from") //登录提交请求（默认）
                .successForwardUrl("/login/success")
//            .successHandler(successHandler)//登录成功后执行的handler
            .failureHandler(failureHandler)//登录失败后执行的handler
            .and()
            .authorizeRequests()// 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/authentication/require","/code/image")
                .permitAll()
            .anyRequest() // 任何请求,登录后可以访问
            .authenticated()
                .and()
                .csrf().disable(); //跨域
    }

    /**
     * 密码加密
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 静态资源过滤
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**","/*.html","/css/**","/img/**","/js/**","/login.html");
    }
}
