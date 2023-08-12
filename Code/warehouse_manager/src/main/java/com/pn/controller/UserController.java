package com.pn.controller;

import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;
import com.pn.service.UserService;
import com.pn.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//  分页查询用户url接口，接收的参数不是JSON类型
//  Page接收参数pageNum、pageSize ；
//  User接收参数 userCode、userType、userState
    @RequestMapping("/user-list")
    public Result getUserList(Page page, User user){
       return Result.ok(userService.queryUserByPage(page,user));
    }

    @Autowired
    private TokenUtils tokenUtils;

//  利用token获取是谁添加的用户
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader("Token") String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        user.setCreateBy(currentUser.getUserId());
        return userService.saveUser(user);
    }

//  启动或禁用用户
    @RequestMapping("/updateState")
    public Result updateState(@RequestBody User user){
        return  userService.setUserState(user);
    }
}
