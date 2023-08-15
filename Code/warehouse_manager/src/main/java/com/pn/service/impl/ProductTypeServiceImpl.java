package com.pn.service.impl;

import com.pn.entity.ProductType;
import com.pn.mapper.ProductMapper;
import com.pn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "com.pn.service.impl.ProductTypeServiceImpl")
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductMapper productMapper;

    @Cacheable(key = "'all:typeTree'")
    @Override
    public List<ProductType> productTypeTree() {
//      查询出所有的商品分类
        List<ProductType> allProductType = productMapper.findAllProductType();
//      将所有商品分类转换成商品分类树
        return allTypeToTypeTree(allProductType,0);
    }

    private List<ProductType> allTypeToTypeTree(List<ProductType> typeList, Integer pid) {
//      拿到n级分类
        List<ProductType> nTypeList = new ArrayList<>();
        for (ProductType productType : typeList) {
            if (productType.getParentId().equals(pid)) {
                nTypeList.add(productType);
            }
        }

//      遍历n级分类，为其找子
        for (ProductType productType : nTypeList) {
//          找子集
            List<ProductType> productTypes = allTypeToTypeTree(typeList, productType.getTypeId());
            productType.setChildProductCategory(productTypes);
        }
        return nTypeList;
    }
}
