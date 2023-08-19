package com.pn.service;

import com.pn.entity.Purchase;
import com.pn.entity.Result;

public interface PurchaseService {
//   添加采购单的业务方法
    public Result savePurchase(Purchase purchase);
}
