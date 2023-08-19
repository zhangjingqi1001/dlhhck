package com.pn.mapper;

import com.pn.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseMapper {
//   添加采购单
    public int insertPurchase(Purchase purchase);

}
