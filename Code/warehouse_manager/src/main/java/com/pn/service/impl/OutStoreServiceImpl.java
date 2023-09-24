package com.pn.service.impl;

import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.mapper.OutStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutStoreServiceImpl implements OutStoreService {

    @Autowired
    private OutStoreMapper outStoreMapper;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public Result saveOutStore(OutStore outStore) {
//      添加
        int success = outStoreMapper.insertOutStore(outStore);

        return success > 0 ? Result.ok("添加出货单成功") : Result.err(501, "添加出货单失败");
    }

    @Override
    @Transactional
    public Result outStoreConfirm(OutStore outStore) {
//      判断商品库存是否充足
        int productInvent = productMapper.findInventById(outStore.getProductId());
        if (productInvent < outStore.getOutNum()) {
           return  Result.err(Result.CODE_ERR_BUSINESS,"商品库存不足！");
        }
//      修改出库单状态
        outStoreMapper.setIsOutById(outStore.getStoreId());
//      修改商品库存
        productMapper.setInventById(outStore.getProductId(),-outStore.getOutNum());

        return Result.ok("确认出库成功");
    }

}
