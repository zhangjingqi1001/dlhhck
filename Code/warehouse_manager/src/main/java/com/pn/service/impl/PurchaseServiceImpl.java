package com.pn.service.impl;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.mapper.PurchaseMapper;
import com.pn.page.Page;
import com.pn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public Result savePurchase(Purchase purchase) {
//      初始化时，实际采购数量要和预计采购数量一致
        purchase.setFactBuyNum(purchase.getBuyNum());
        int success = purchaseMapper.insertPurchase(purchase);

        return success>0 ? Result.ok("添加采购单成功") : Result.err(501,"添加采购单失败");
    }

    @Override
    public Page queryPurchasePage(Page page, Purchase purchase) {

//      查询采购单行数
        Integer count = purchaseMapper.findPurchaseCount(purchase);

//      分页查询采购单
        List<Purchase> purchasePage = purchaseMapper.findPurchasePage(page, purchase);

//      组装分页信息
        page.setTotalNum(count);
        page.setResultList(purchasePage);
        return page;
    }

}
