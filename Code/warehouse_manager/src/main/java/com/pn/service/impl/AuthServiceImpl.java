package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Auth;
import com.pn.mapper.AuthMapper;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "com.pn.service.impl.AuthServiceImpl")
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询用户菜单树的业务方法
     * 向Redis中缓存 - 键authTree:userId,值菜单树List<Auth>转的JSON字符串
     */
    @Override
    public List<Auth> queryAuthTreeByUid(Integer userId) {

//      先从Redis中查找缓存中的用户菜单树
        String authTreeJson = redisTemplate.opsForValue().get("authTree:" + userId);
        if (StringUtils.hasText(authTreeJson)) {
//          说明Redis中有用户中有用户菜单树的缓存
//          将JSON串转成集合对象并返回
            return JSON.parseArray(authTreeJson, Auth.class);
        }
//      说明Redis中没有用户中有用户菜单树的缓存
//      查询用户权限下的所有菜单
        List<Auth> allAuthList = authMapper.findAuthByUid(userId);
//      将所有菜单List<Auth>转成菜单树List<Auth>
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
//      向Redis中缓存一份
        redisTemplate.opsForValue().set("authTree:" + userId,JSON.toJSONString(authTreeList));
        return authTreeList;
    }



    //  将所有菜单List<Auth>转成菜单树List<Auth>
//  第一次的话pid是0
    private  List<Auth> allAuthToAuthTree( List<Auth> allAuthList,Integer pid){
        List<Auth> firstLevelAuthList = new ArrayList<>();

//      查询出所有n级菜单(比如说一级菜单)
        for (Auth auth:allAuthList){
//          pid=0，说明就是1级菜单
           if ( auth.getParentId().equals(pid)){
               firstLevelAuthList.add(auth);
           }
        }
//      拿到每一个n级菜单的(n+1)级菜单
        for (Auth firstAuth: firstLevelAuthList){
//          递归,获取(n+1)级菜单
            List<Auth> secondLevelAuthList = allAuthToAuthTree(allAuthList,firstAuth.getAuthId());
            firstAuth.setChildAuth(secondLevelAuthList);
        }

        return firstLevelAuthList;
    }

    @Cacheable("'all:authTree'")
    @Override
    public List<Auth> allAuthTree() {
//      查询出所有权限菜单
        List<Auth> allAuth = authMapper.findAllAuth();
//      将所有权限菜单转成菜单树
        return allAuthToAuthTree(allAuth, 0);
    }

    @Override
    public List<Integer> findAuthByRid(Integer roleId) {
        return authMapper.findAuthIdByRid(roleId);
    }


}
