package com.pn.service;

import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    //获取所有的角色
    public List<Role> getAllRole();

    //根据userId查询对应角色
    public List<Role> getRoleByUserId(Integer userId);

   //分页查询角色的业务方法
    public Page queryRolePage(Page page,Role role);

    //添加角色的业务方法
    public Result saveRole(Role role);

    //根据角色id修改角色状态的方法
    public Result setRoleStateByRid(Role role);

}
