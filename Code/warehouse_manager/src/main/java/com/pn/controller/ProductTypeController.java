package com.pn.controller;


import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/productCategory")
@RestController
public class ProductTypeController {

//   注入
    @Autowired
    private ProductTypeService productTypeService;

//  查询商品分类树的url
    @RequestMapping("/product-category-tree")
    public Result productCategoryTree(){
//      执行业务
     return  Result.ok(productTypeService.productTypeTree())  ;
    }

//  校验分类编码是否已经存在
    @RequestMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode){
//      执行业务
       return productTypeService.checkTypeCode(typeCode);
    }

//  添加商品分类的url接口
    @RequestMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType){
        return productTypeService.saveProductType(productType);
    }

//  删除商品分类
    @RequestMapping("/type-delete/{typeId}")
    public Result typeDelete(@PathVariable Integer typeId){
       return productTypeService.deleteProductType(typeId);
    }

}
