package com.pn.service;

import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;

public interface UserService {
//  根据账号查询用户的业务方法
    public User queryUserByCode(String userCode);

//  分页查询的用户方法
    public Page queryUserByPage(Page page, User user);

//  添加用户的业务方法
    public Result saveUser(User user);

//   启动或禁用用户
    public Result setUserState( User user);
}
