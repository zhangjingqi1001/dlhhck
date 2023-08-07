package com.pn.service;

import com.pn.entity.User;

public interface UserService {
//    根据账号查询用户的业务方法
    public User queryUserByCode(String userCode);
}
