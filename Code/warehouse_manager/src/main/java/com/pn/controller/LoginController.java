package com.pn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.pn.entity.Result;
import com.pn.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping()
public class LoginController {

    //  Producer是一个接口，注入的是在CaptchaConfig配置类中注入的对象 - 生成验证码图片的对象
    @Resource(name = "captchaProducer")//按照名字进行注入
    private Producer producer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //  要通过响应对象把生成的验证码图片返回给前端
    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) throws IOException {
//      生成验证码图片的文本
        String text = producer.createText();

//      使用验证码文本生成验证码图片 - BufferedImage缓冲图片，在内存里
        BufferedImage image = producer.createImage(text);

//      把验证码图片的文本作为键保存到Redis中
        redisTemplate.opsForValue().set(text, "", 30, TimeUnit.MINUTES);

//      将验证码图片响应给前端
//      设置响应正文image/jpeg - Content-Type头字段用于告诉浏览器或客户端服务器返回的数据的MIME类型（Multipurpose Internet Mail Extensions）
        response.setContentType("image/jpeg");
//      将验证码图片写给前端
        ServletOutputStream outputStream = response.getOutputStream();
//      write方法的参数中
//          参数一：ReaderImage代表内存中图片，参数二：格式名，参数三：所用到的字节输出流
        ImageIO.write(image, "jpg", outputStream);

        outputStream.flush();

        if (outputStream != null) {
            outputStream.close();
        }

    }

//  登录URL接口
//    @RequestMapping("/login")
//    public

    @PostMapping("/test")
    public ResponseEntity test(){
        User user = new User();
        user.setUserId(10086);
        user.setUserCode("10088888");

//      将对象转换成字符串
        String userJsonStr = JSON.toJSONString(user);
        log.info("userJsonStr - "+ userJsonStr);

//      再将JSON字符串转换成JSONObject类型
        JSONObject jsonObject = JSON.parseObject(userJsonStr);
        log.info("jsonObject - "+jsonObject);
        jsonObject.put("aaa0","sssss");
        return  new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

}