package com.l024.wmzbsecurityborowser.config;

import com.l024.wmzbsecurityborowser.security.MyUserDetailsService;
import com.l024.wmzbsecurityborowser.session.WmzbExpiredSessionStrategy;
import com.l024.wmzbsecuritycore.authentication.moblie.SecurityAuthenticationFailureHandler;
import com.l024.wmzbsecuritycore.authentication.moblie.SecurityAuthenticationSuccessHandler;
import com.l024.wmzbsecuritycore.authentication.moblie.SmsCodeAuthenticationSecurityConfig;
import com.l024.wmzbsecuritycore.authentication.moblie.ValidateSmsCodeFilter;
import com.l024.wmzbsecuritycore.properties.SecurityProperties;
import com.l024.wmzbsecuritycore.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

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
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private ValidateSmsCodeFilter validateSmsCodeFilter;

    @Autowired
    private DataSource datasource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    //短信验证配置类
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

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
        //短信验证码验证过滤器
        validateSmsCodeFilter.setAuthenticationFailureHandler(failureHandler);
        http
             //自定义过滤器 在UsernamePasswordAuthenticationFilter之前执行，
            .addFilterBefore(validateSmsCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .apply(springSocialConfigurer)
                .and()
                .formLogin()// 定义当需要用户登录时候，转到的登录页面。
            .loginPage("/authentication/require")//自定义登录
            .loginProcessingUrl("/authentication/from") //登录提交请求（默认）
                .successForwardUrl("/login/success")
//            .successHandler(successHandler)//登录成功后执行的handler
            .failureHandler(failureHandler)//登录失败后执行的handler
            .and()
            .rememberMe()//记住密码
            .tokenRepository(persistentTokenRepository())//设置tokenPepository
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//设置过期时间
            .userDetailsService(userDetailsService)
            .and()
                //处理session过期之后跳转的地址
                .sessionManagement()
                .invalidSessionUrl("/session/invalid")
                //最大允许多少设备使用session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)//第一个账号登录成功后第二个账号无法登录
                //session只能允许一台设置登录。另外一台登录后触发session过期并跳转的执行方法
                .expiredSessionStrategy(new WmzbExpiredSessionStrategy())
                .and()
                .and()
                .logout()
                .logoutUrl("/signOut")//配置退出的访问url
                .logoutSuccessUrl("/logoutSuccess")//退出成功跳转的页面
                .and()
            .authorizeRequests()// 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/authentication/require","/code/*","/authentication/mobile","/auth/*","/session/invalid","/signOut","/logoutSuccess","/oauth/*")
                .permitAll()
            .anyRequest() // 任何请求,登录后可以访问
            .authenticated()
                .and()
                .csrf()
                .disable()//跨域
                .apply(smsCodeAuthenticationSecurityConfig); //加载短信验证配置类
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
     * 记住我
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        //token存入数据库
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(datasource);
        //启动的时候创建表
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
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
