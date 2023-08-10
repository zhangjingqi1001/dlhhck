package com.pn.filter;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//自定义的登录限制过滤器
@Slf4j
public class LoginCheckFilter implements Filter {

    private StringRedisTemplate redisTemplate;

    public LoginCheckFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //  过滤器拦截到请求执行的方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//      TODO 1.白名单请求直接放行
        List<String> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");

//      过滤器拦截到的当前请求的资源路径
        String url = request.getServletPath();
        log.info("资源路径 - " + url);//也就是“/captcha/captchaImage”形式
        if (urlList.contains(url)) {
//          放行
            filterChain.doFilter(request, response);
//          不需要执行后面的代码
            return;
        }
//      项目路径
        String contextPath = request.getContextPath();
        log.info("项目路径 - " + contextPath);// 也就是“/warehouse”形式

//      TODO 2.其他请求都校验是否携带token，携带了后判断Redis中是否存在token的键
        String token = request.getHeader("Token");
//      1>有，说明已经登录，请求放行
        if (StringUtils.hasText(token)&& Boolean.TRUE.equals(redisTemplate.hasKey(token))){
            //          放行
            filterChain.doFilter(request, response);
//          不需要执行后面的代码
            return;
        }
//      2>没有，说明未登录或token过期，请求不放行，给前端做出响应

        String jsonString = JSON.toJSONString(Result.err(401,"您尚未登录！"));
//      响应正文,application/json表示响应的为JSON，charset=UTF-8表示响应编码
        response.setContentType("application/json;charset=UTF-8");
//      拿到响应对象的字符输出流【
        PrintWriter out = response.getWriter();
        out.write(jsonString);
        out.flush();
        out.close();
    }
}
