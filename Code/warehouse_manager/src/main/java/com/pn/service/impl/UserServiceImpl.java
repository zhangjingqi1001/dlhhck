package com.pn.service.impl;

import com.pn.entity.User;
import com.pn.mapper.UserMapper;
import com.pn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }
}
