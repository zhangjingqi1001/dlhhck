package com.pn.controller;

import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.BrandService;
import com.pn.service.ProductService;
import com.pn.service.StoreService;
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
}
