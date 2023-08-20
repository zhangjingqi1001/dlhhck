package com.pn.service;

import com.pn.entity.ProductType;
import com.pn.entity.Result;

import java.util.List;

public interface ProductTypeService {

//  查询商品分类树的业务方法
    public List<ProductType> productTypeTree();

//  校验分类编码否存在的业务方法
    public Result checkTypeCode(String typeCode);

//  添加商品分类的业务方法
    public Result saveProductType(ProductType productType);

//  删除分类的业务方法
    public Result deleteProductType(Integer typeId);

}
