package com.pn.service;

import com.pn.entity.Auth;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.connection.RedisServer;

import java.util.List;

public interface AuthService {
    //查询用户菜单树的业务方法
    public List<Auth> queryAuthTreeByUid(Integer userId);

    //查询所有权限菜单树的业务方法
    public List<Auth> allAuthTree();

    //根据角色id查询分配的所有权限菜单的方法
    public List<Integer> findAuthByRid(Integer roleId);
}
