package com.pn.service.impl;

import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.mapper.RoleMapper;
import com.pn.page.Page;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称（数据保存到redis中的键的前缀）
@CacheConfig(cacheNames = "com.pn.service.impl.RoleServiceImpl")
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Cacheable(key = "'all:role'")
    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    public List<Role> getRoleByUserId(Integer userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Override
    public Page queryRolePage(Page page, Role role) {
        Integer roleRowCount = roleMapper.findRoleRowCount(role);
//      设置总数
        page.setTotalNum(roleRowCount);
//      设置从哪开始
        page.setLimitIndex(page.getLimitIndex());
//      设置总页数
        page.setPageCount(page.getPageCount());
//      分页查询数据
        List<Role> rolePage = roleMapper.findRolePage(page, role);
        page.setResultList(rolePage);
        return page;
    }

    @Cacheable(key = "'all:role'")//记得清除一个Redis缓存中role角色信息
    @Override
    public Result saveRole(Role role) {
        Role roleByNameOrCode = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (roleByNameOrCode != null){
            return Result.err(Result.CODE_ERR_BUSINESS, "添加角色失败!角色已存在");
        }
        int success = roleMapper.insertRole(role);

        return success > 0 ? Result.ok("添加成功") : Result.err(Result.CODE_ERR_BUSINESS, "角色添加失败");
    }

    @Cacheable(key = "'all:role'")//记得清除一个Redis缓存中role角色信息
    @Override
    public Result setRoleStateByRid(Integer roleId, String roleState) {
        int success = roleMapper.setRoleStateByRid(roleId, roleState);
        return success > 0 ? Result.ok("状态修改成功") : Result.err(Result.CODE_ERR_BUSINESS, "状态修改失败");
    }

}
