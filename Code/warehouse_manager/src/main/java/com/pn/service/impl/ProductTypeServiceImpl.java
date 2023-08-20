package com.pn.service.impl;

import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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

    @Override
    public Result checkTypeCode(String typeCode) {
//      根据分类编码查询分类，并判断是否存在
        ProductType productType  = new ProductType();
        productType.setTypeCode(typeCode);
        ProductType prodType = productMapper.findTypeByCodeOrName(productType);

        return Result.ok(prodType == null);
    }

    @CacheEvict(key = "'all:typeTree'")
    @Override
    public Result saveProductType(ProductType productType) {
//      首先校验一下分类名称是否存在
        ProductType prodType = new ProductType();
        prodType.setTypeName(productType.getTypeName());
        ProductType prodCategory = productMapper.findTypeByCodeOrName(prodType);
        if (prodCategory!=null){
            return Result.err(501,"分类名称已经存在");
        }

//      分类名称不存在，添加分类
        int success = productMapper.insertProductType(productType);

        return success>0 ? Result.ok("添加分类成功") : Result.err(501,"添加分类失败");
    }

    @CacheEvict(key = "'all:typeTree'")
    @Override
    public Result deleteProductType(Integer typeId) {
        int success = productMapper.removeProductType(typeId);

        return success>0? Result.ok("删除成功") : Result.err(501,"删除失败");
    }


}
