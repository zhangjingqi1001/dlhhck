package com.pn.service.impl;

import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.mapper.UserMapper;
import com.pn.page.Page;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    //分页查询的用户方法
    @Override
    public Page queryUserByPage(Page page, User user) {
        Integer rowCount = userMapper.findRowCount(user);
//      设定数据总行数
        page.setTotalNum(rowCount);
//      设定一共多少页
        page.setPageCount(page.getPageCount());
//      设定每一页的起始行
        page.setLimitIndex(page.getLimitIndex());

        List<User> userByPage = userMapper.findUserByPage(page, user);

        page.setResultList(userByPage);

        return page;
    }

    //添加用户的业务
    @Override
    public Result saveUser(User user) {
//      用户的账号不能重复
        User userByCode = userMapper.findUserByCode(user.getUserCode());
        if (userByCode !=null){
            return Result.err(Result.CODE_ERR_BUSINESS,"添加用户失败!账号重复");
        }
//      对密码加密
        String password = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(password);
        int success = userMapper.addUser(user);

        return success>0? Result.ok("添加成功") : Result.err(Result.CODE_ERR_BUSINESS,"添加用户失败");
    }

    //启动或禁用用户
    @Override
    public Result setUserState(User user) {
        int success = userMapper.updateStateByUid(user.getUserId(), user.getUserState());

        return success>0? Result.ok("修改成功"):Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }


}
