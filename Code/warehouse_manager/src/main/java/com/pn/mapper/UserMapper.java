package com.pn.mapper;

import com.pn.entity.User;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * user_info表的Mapper接口
 */
@Mapper
public interface UserMapper {
//  根据账号查询用户信息的方法啊
    public User findUserByCode(String userCode);

//  查询用户总行数的方法
    public Integer findRowCount(User user);

//  分页查询用户的方法
    public List<User> findUserByPage(@Param("page") Page page,@Param("user") User user);

//  添加用户
    public int addUser(User user);

//  根据用户id修改用户的状态
    public int updateStateByUid(@Param("userId") int userId,@Param("userState") String userState);

//  根据用户ids修改用户为删除状态的方法
    public int setIsDeleteByUids(List<Integer> userIdList);



}
