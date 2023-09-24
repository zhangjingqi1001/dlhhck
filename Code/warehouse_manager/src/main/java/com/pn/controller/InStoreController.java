package com.pn.controller;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/instore")
public class InStoreController {

    //  注入InStoreService
    @Autowired
    private InStoreService inStoreService;

    @Autowired
    private StoreService storeService;

    //  查询所有仓库的url接口
    @RequestMapping("/store-list")
    public Result storeList() {
//      所有仓库
        return Result.ok(storeService.queryAllStore());
    }

    //  分页查询
    @RequestMapping("/instore-page-list")
    public Result inStoreListPage(Page page, InStore inStore) {

        return Result.ok(inStoreService.queryInStore(page, inStore));
    }

    //确认入库的url
    @RequestMapping("/instore-confirm")
    public Result confirmInStore(@RequestBody InStore inStore) {
        return inStoreService.inStoreConfirm(inStore);
    }


}
