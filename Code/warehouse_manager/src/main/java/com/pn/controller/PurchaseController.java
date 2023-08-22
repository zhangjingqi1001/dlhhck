package com.pn.controller;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.page.Page;
import com.pn.service.PurchaseService;
import com.pn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StoreService storeService;

    //添加采购单
    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase) {

        return purchaseService.savePurchase(purchase);
    }

    //仓库数据回显 - 查询所有仓库
    @RequestMapping("/store-list")
    public Result storeList() {
        return Result.ok(storeService.queryAllStore());
    }

    //分页查询采购单的url
    @RequestMapping("/purchase-page-list")
    public Result purchaseListPage(Page page, Purchase purchase) {
        return Result.ok(purchaseService.queryPurchasePage(page,purchase));
    }


}
