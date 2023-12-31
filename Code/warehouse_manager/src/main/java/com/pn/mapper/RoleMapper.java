package com.pn.mapper;

import com.pn.entity.Role;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
//  查询所有角色的方法
    public List<Role> getAllRole();

//  根据userId查询对应角色
    public  List<Role> getRoleByUserId(@Param("userId") Integer userId);

//  根据角色名查询角色id
    public Integer findRoleIdByName(String roleName);

//  根据用户Id删除用户已经分配的角色关系
    public int removeUserRoleByUid(Integer userId);

//  向user_role表添加用户角色对应关系
    public int insertUserRole(@Param("userId") Integer userId,@Param("roleId")Integer roleId);

//  查询角色行数的方法
    public Integer findRoleRowCount(@Param("role") Role role);
//  分页查询角色的方法
    public List<Role> findRolePage(@Param("page")Page page,@Param("role")Role role);

//  根据角色名称或角色代码查询角色的方法
    public Role findRoleByNameOrCode(@Param("roleName")String roleName,@Param("roleCode")String roleCode);

//  添加角色的方法
    public int insertRole(@Param("role")Role role);

//  根据角色id修改角色状态的方法
    public int setRoleStateByRid(@Param("roleId")Integer roleId,@Param("roleState")String roleState);

//  根据角色id删除角色的方法
    public int removeRoleById(@Param("roleId") Integer roleId);
//  根据角色id删除user_role表中对应关系
    public int deleteRoleUserRelation(@Param("roleId") Integer roleId);
}
