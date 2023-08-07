package com.pn.mapper;

import com.pn.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * user_info表的Mapper接口
 */
@Mapper
public interface UserMapper {
//    根据账号查询用户信息的方法啊
    public User findUserByCode(String userCode);
}
