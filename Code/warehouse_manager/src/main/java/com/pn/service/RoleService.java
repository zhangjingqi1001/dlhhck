package com.pn.service;

import com.pn.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    //获取所有的角色
    public List<Role> getAllRole();

    //  根据userId查询对应角色
    public  List<Role> getRoleByUserId(Integer userId);
}
