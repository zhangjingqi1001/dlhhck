package com.pn.controller;

import com.google.code.kaptcha.Producer;
import com.pn.entity.*;
import com.pn.service.AuthService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
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

    @Autowired
    private UserService userService;


    //  登录URL接口
    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {
//      验证验证码是否正确
        String verificationCode = loginUser.getVerificationCode();
//      与Redis存储的验证码进行比较
        if (!redisTemplate.hasKey(verificationCode)){
//          Redis中没有对应的验证码
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码输入不正确");
        }

//      根据账号查询用户
        User user = userService.queryUserByCode(loginUser.getUserCode());
//      判断账号是否存在
        if (user != null) {
//          账号存在
//          user表里有个字段user_state，表示用户是否已经被审核，0未审核，1已审核
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {
//              代表是1，表示用户已经被审核，之后校验密码
//              数据库中的密码是MD5加密的（MD5加密的数据不能解密），请求携带的密码是明文
//              所以只能将明文密码加密与数据库中加密的数据对比
                String userPwd = loginUser.getUserPwd();
                userPwd = DigestUtil.hmacSign(userPwd);
                if (userPwd.equals(user.getUserPwd())) {
//                  密码正确，生成JWTToken，颁发给浏览器
                    CurrentUser currentUser = new CurrentUser();
                    currentUser.setUserId(user.getUserId());
                    currentUser.setUserName(user.getUserName());
                    currentUser.setUserCode(user.getUserName());
//                  生成JWT Token并存储到Redis
                    String token = tokenUtils.loginSign(currentUser, user.getUserPwd());
//                  将token颁发给浏览器
                    return Result.ok("登录成功",token);
                } else {
//                  密码错误
                    return Result.err(Result.CODE_ERR_BUSINESS, "密码错误");
                }

            } else {
//              用户未审核
                return Result.err(Result.CODE_ERR_BUSINESS, "用户未审核");
            }
        } else {
//           账号不存在
            return Result.err(Result.CODE_ERR_BUSINESS, "账号不存在");
        }

    }

    @Autowired
    private TokenUtils tokenUtils;

    //我们需要获取请求头，或者可以直接使用@RequestHeader("token") String token
    @RequestMapping("/curr-user")
    public Result currentUser(HttpServletRequest request){
        String token = request.getHeader("token");
        log.info("token - "+token);
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        return Result.ok(currentUser);
    }

    @Autowired
    private AuthService authService;

    @RequestMapping("/user/auth-list")
    public Result loadAuthTree(@RequestHeader("token") String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        return Result.ok(authService.queryAuthTreeByUid(currentUser.getUserId()));
    }
}