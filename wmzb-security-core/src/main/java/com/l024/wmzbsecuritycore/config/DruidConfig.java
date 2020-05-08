package com.l024.wmzbsecuritycore.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * druid数据连接池配置类
 */
@Configuration
public class DruidConfig {
    //数据源
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        //转list
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return druidDataSource;
    }

    @Bean
    public Filter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(5000);
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        return statFilter;
    }

    //配置Druid监控的访问页面等配置
    //配置Druid监控
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123");
        initParams.put("allow","");//可以配置只有谁可以访问，默认就是所有都可以访问
        initParams.put("deny","192.168.12.10");//配置谁不可以访问
        bean.setInitParameters(initParams);//把初始化参数放入Servlet中
        return bean;
    }

    //配置Druid过滤器
    //配置一个web监控的Filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("exclusions","*.js,*.css,/druid/*,/templates/*");//设置不拦截的静态资源和请求
        bean.setInitParameters(initParams);
        bean.setFilter(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
