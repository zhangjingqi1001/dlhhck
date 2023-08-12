package com.pn.controller;

import com.pn.entity.Result;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
