package com.pn.service.impl;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.mapper.InStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.mapper.PurchaseMapper;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InStoreServiceImpl implements InStoreService {

    //注入InStoreMapper
    @Autowired
    private InStoreMapper inStoreMapper;

    //注入PurchaseMapper
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private ProductMapper productMapper;


//    //添加入库单的业务方法
    @Transactional//事务处理
    @Override
    public Result saveInStore(InStore inStore, Integer buyId) {
        //添加入库单
        int i = inStoreMapper.insertInStore(inStore);
        if(i>0){
            //根据id将采购单状态改为已入库
            int j = purchaseMapper.setIsInById(buyId);
            if(j>0){
                return Result.ok("入库单添加成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
    }

    @Override
    public Page queryInStore(Page page, InStore inStore) {
//      查询入库单行数
        Integer count = inStoreMapper.findInStoreCount(inStore);

//      分页查询入库单
        List<InStore> inStorePageList = inStoreMapper.findInStorePage(page, inStore);

        page.setResultList(inStorePageList);
        page.setTotalNum(count);

        return page;
    }

    @Override
    @Transactional
    public Result inStoreConfirm(InStore inStore) {
//      修改入库单状态
        int success = inStoreMapper.setIsInById(inStore.getInsId());
        if (success>0){
//          修改商品库存
//          商品入库数量增加
            int successCount = productMapper.setInventById(inStore.getProductId(), inStore.getInNum());
            if (successCount>0){
                return Result.ok("入库单确认成功！");
            }
        }

        return Result.err(Result.CODE_ERR_BUSINESS,"入库单确认失败！");
    }

}
