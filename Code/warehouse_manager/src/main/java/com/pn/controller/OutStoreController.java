package com.pn.controller;

import com.pn.entity.CurrentUser;
import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.OutStoreService;
import com.pn.service.StoreService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/outstore")
@RestController
public class OutStoreController {

    //注入OutStoreService
    @Autowired
    private OutStoreService outStoreService;

    //注入TokenUtils
    @Autowired
    private TokenUtils tokenUtils;

    //注入StoreService
    @Autowired
    private StoreService storeService;


    @RequestMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore,
                              @RequestHeader("Token") String token){

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加出库单的用户id
        int createBy = currentUser.getUserId();
        outStore.setCreateBy(createBy);

        //响应
        return outStoreService.saveOutStore(outStore);
    }

}
