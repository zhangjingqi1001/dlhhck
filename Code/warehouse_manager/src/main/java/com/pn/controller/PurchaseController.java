package com.pn.controller;

import com.pn.entity.CurrentUser;
import com.pn.entity.InStore;
import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.PurchaseService;
import com.pn.service.StoreService;
import com.pn.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StoreService storeService;

    //注入InStoreService
    @Autowired
    private InStoreService inStoreService;

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
        return Result.ok(purchaseService.queryPurchasePage(page, purchase));
    }

    //  删除采购单
    @RequestMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable Integer buyId) {
        return Result.ok(purchaseService.deletePurchaseById(buyId));
    }

    //  修改采购单的业务方法
    @RequestMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase) {
        return Result.ok(purchaseService.updatePurchaseById(purchase));
    }

    //注入TokenUtils
    @Autowired
    private TokenUtils tokenUtils;

    //  生成入库单的url接口
    @RequestMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody Purchase purchase, @RequestHeader("Token") String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        //获取当前登录的用户id 创建入库单的用户id
        int createBy = currentUser.getUserId();

        //创建InStore对象封装添加的入库单的信息
        InStore inStore = new InStore();
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getFactBuyNum());
        inStore.setCreateBy(createBy);

        return inStoreService.saveInStore(inStore,purchase.getBuyId());
    }

}
