package com.pn.config;

import com.pn.filter.LoginCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class ServletConfig {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
//      创建FilterRegistrationBean的Bean对象
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
//      创建自定义的过滤器
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter(stringRedisTemplate);
//      将自定义的过滤器注册到FilterRegistrationBean
        filterRegistrationBean.setFilter(loginCheckFilter);
//      给过滤器指定拦截的请求
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
