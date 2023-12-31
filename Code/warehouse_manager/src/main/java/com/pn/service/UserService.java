package com.pn.service;

import com.pn.dto.AssignRoleDto;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
//  根据账号查询用户的业务方法
    public User queryUserByCode(String userCode);

//  分页查询的用户方法
    public Page queryUserByPage(Page page, User user);

//  添加用户的业务方法
    public Result saveUser(User user);

//   启动或禁用用户
    public Result setUserState( User user);

//  给用户修改角色信息
    public Result changeUserRole(AssignRoleDto assignRoleDto);

//  删除用户的业务方法
    public Result deleteUserByIds(List<Integer> userIdList);

//  修改用户的业务方法
    public Result setUserById(Integer userId, String userName, int updateBy);

//  根据用户id修改密码的方法
    public Result setPwdByUid(Integer userId);
}
