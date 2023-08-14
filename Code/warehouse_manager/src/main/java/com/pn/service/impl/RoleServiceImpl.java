package com.pn.service.impl;

import com.pn.dto.AssignAuthDto;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.mapper.AuthMapper;
import com.pn.mapper.RoleMapper;
import com.pn.page.Page;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//指定缓存的名称（数据保存到redis中的键的前缀）
@CacheConfig(cacheNames = "com.pn.service.impl.RoleServiceImpl")
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthMapper authMapper;

    @Cacheable(key = "'all:role'")
    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    public List<Role> getRoleByUserId(Integer userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Transactional
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

    @Transactional
    @CacheEvict(key = "'all:role'")//记得清除一个Redis缓存中role角色信息
    @Override
    public Result saveRole(Role role) {
        Role roleByNameOrCode = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (roleByNameOrCode != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "添加角色失败!角色已存在");
        }
        int success = roleMapper.insertRole(role);

        return success > 0 ? Result.ok("添加成功") : Result.err(Result.CODE_ERR_BUSINESS, "角色添加失败");
    }

    @CacheEvict(key = "'all:role'")//记得清除一个Redis缓存中role角色信息
    @Override
    public Result setRoleStateByRid(Role role) {
        int success = roleMapper.setRoleStateByRid(role.getRoleId(), role.getRoleState());
        return success > 0 ? Result.ok("状态修改成功") : Result.err(Result.CODE_ERR_BUSINESS, "状态修改失败");
    }

    @Transactional
    @CacheEvict(key = "'all:role'")//记得清除一个Redis缓存中role角色信息
    @Override
    public Result deleteRoleById(Integer roleId) {
        int successRole = roleMapper.removeRoleById(roleId);
        if (successRole > 0) {
//           删除用户角色关系中对应内容
            roleMapper.deleteRoleUserRelation(roleId);
//           删除角色权限关系
            authMapper.deleteRoleAuthRelation(roleId);
        }
        return Result.ok("删除成功");
    }

    @Transactional
    @Override
    public void saveRoleAuth(AssignAuthDto assignAuthDto) {
//       删除角色与权限之前的对应关系
        authMapper.deleteRoleAuthRelation(assignAuthDto.getRoleId());
//      添加角色权限关系
        List<Integer> authIds = assignAuthDto.getAuthIds();
        for (Integer authId : authIds){
            authMapper.insertRoleAuth(assignAuthDto.getRoleId(),authId);
        }

    }

}
