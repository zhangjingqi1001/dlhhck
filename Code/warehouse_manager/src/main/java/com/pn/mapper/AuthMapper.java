package com.pn.mapper;

import com.pn.entity.Auth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthMapper {
//  根据userId查询用户权限下的所有菜单的方法
    public List<Auth> findAuthByUid(Integer userId);

    //根据角色id删除user_role表中对应关系
    public int deleteRoleAuthRelation(@Param("roleId") Integer roleId);
}
