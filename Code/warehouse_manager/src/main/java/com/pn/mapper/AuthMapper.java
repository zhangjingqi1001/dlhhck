package com.pn.mapper;

import com.pn.entity.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper {
//  根据userId查询用户权限下的所有菜单的方法
    public List<Auth> findAuthByUid(Integer userId);
}
