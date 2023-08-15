package com.pn.controller;

import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    //  注入StoreService
    @Autowired
    private StoreService storeService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    //  查询所有仓库
    @RequestMapping("/store-list")
    public Result storeList() {
        return Result.ok(storeService.queryAllStore());
    }

    //  查询所有品牌
    @RequestMapping("/brand-list")
    public Result brandList() {
        return Result.ok(brandService.queryAllBrand());
    }

    //分页查询商品的url接口
    @RequestMapping("/product-page-list")
    public Result productListPage(Page page, Product product) {
        page = productService.queryProductPage(page, product);
        return Result.ok(page);
    }

    @Autowired
    private ProductTypeService productTypeTree;

    //查询所有商品分类树的接口
    @RequestMapping("/category-tree")
    public Result loadTypeTree(){
        return Result.ok(productTypeTree.productTypeTree());
    }

    @Autowired
    private SupplyService supplyService;

    // 查询所有供应商的接口
    @RequestMapping("/supply-list")
    public Result supplyList(){
        return Result.ok(supplyService.supplyMapper());
    }


    @Autowired
    private PlaceService placeService;

    @RequestMapping("/place-list")
    public Result placeList(){
//      执行业务
        return Result.ok(placeService.queryAllPlace());
    }

    @Autowired
    private UnitService unitService;

    @RequestMapping("/unit-list")
    public Result unitList(){
//      执行业务
        return Result.ok(unitService.queryAllUnit());
    }

}
