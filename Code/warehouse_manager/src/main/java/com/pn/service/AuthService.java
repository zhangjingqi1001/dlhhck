package com.pn.service;

import com.pn.entity.Auth;

import java.util.List;

public interface AuthService {
//   查询用户菜单树的业务方法
    public List<Auth> queryAuthTreeByUid(Integer userId );
}
