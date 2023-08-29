package com.pn.mapper;

import com.pn.entity.Purchase;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseMapper {
//   添加采购单
    public int insertPurchase(Purchase purchase);

//  查询采购单行数的方法
    public Integer findPurchaseCount(Purchase purchase);

//  分页查询采购单的方法
    public List<Purchase> findPurchasePage(@Param("page") Page page,@Param("purchase") Purchase purchase);

//  根据id删除采购单的方法
    public int removerPurchaseById(@Param("buyId") Integer buyId);

//  根据id修改预计采购数量和实际采购数量的方法
    public int setNumberById(Purchase purchase);

//  根据id修改采购单状态为已入库
    public int setIsInById(Integer buyId);

}
