package com.pn.controller;

import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;
import com.pn.service.AuthService;
import com.pn.service.RoleService;
import com.pn.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    //注入RoleService
    @Autowired
    private RoleService roleService;

    //查询所有角色
    @RequestMapping("/role-list")
    public Result roleList() {
        return Result.ok(roleService.getAllRole());
    }

    //分页查询角色
    @RequestMapping("/role-page-list")
    public Result roleListPage(Page page, Role role) {
        return Result.ok(roleService.queryRolePage(page, role));
    }

    @Autowired
    private TokenUtils tokenUtils;

    //添加角色
    @RequestMapping("/role-add")
    public Result addRole(@RequestBody Role role, @RequestHeader("Token") String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        role.setCreateBy(currentUser.getUserId());

        return roleService.saveRole(role);
    }

    //启动或禁用角色的url接口
    @RequestMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role) {
        return roleService.setRoleStateByRid(role);
    }

    //删除角色的url接口
    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable("roleId") Integer roleId){
     return roleService.deleteRoleById(roleId);
    }

    @Autowired
    private AuthService authService;

    @RequestMapping("/role-auth")
    public Result roleAuth(Integer roleId){
        return  Result.ok( authService.findAuthByRid(roleId));

    }
}
