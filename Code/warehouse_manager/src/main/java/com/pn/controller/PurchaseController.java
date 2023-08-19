package com.pn.controller;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase){

        return purchaseService.savePurchase(purchase);
    }

}
