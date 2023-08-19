package com.pn.service.impl;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.mapper.PurchaseMapper;
import com.pn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
