package com.pn.controller;

import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;
import com.pn.service.RoleService;
import com.pn.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
