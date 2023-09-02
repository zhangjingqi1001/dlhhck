package com.pn.mapper;

import com.pn.dto.AssignAuthDto;
import com.pn.entity.Auth;
import com.pn.entity.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuthMapper {
//  根据userId查询用户权限下的所有菜单的方法
    public List<Auth> findAuthByUid(Integer userId);

    //根据角色id删除role_auth表中对应关系
    public int deleteRoleAuthRelation(@Param("roleId") Integer roleId);

//  查询所有权限菜单的方法
    public List<Auth> findAllAuth();

//  根据角色id查询分配的所有权限菜单的方法
    public List<Integer> findAuthIdByRid(@Param("roleId") Integer roleId);

//  添加角色权限关系的方法
    public int insertRoleAuth(@Param("roleId") Integer roleId,@Param("authId") Integer authId);

    void checkScore(Map<String,Object> param);


    public List<Auth> checkScoreAndGetAuth(Map<String,Object> param);

    public List<Brand> checkScoreAndGetBrand(Map<String,Object> param);

}
