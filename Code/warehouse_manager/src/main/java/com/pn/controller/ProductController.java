package com.pn.controller;

import com.pn.entity.CurrentUser;
import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.*;
import com.pn.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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
    public Result loadTypeTree() {
        return Result.ok(productTypeTree.productTypeTree());
    }

    @Autowired
    private SupplyService supplyService;

    // 查询所有供应商的接口
    @RequestMapping("/supply-list")
    public Result supplyList() {
        return Result.ok(supplyService.supplyMapper());
    }


    @Autowired
    private PlaceService placeService;

    @RequestMapping("/place-list")
    public Result placeList() {
//      执行业务
        return Result.ok(placeService.queryAllPlace());
    }

    @Autowired
    private UnitService unitService;

    @RequestMapping("/unit-list")
    public Result unitList() {
//      执行业务
        return Result.ok(unitService.queryAllUnit());
    }


    @Value("${file.upload-path}")
    private String uploadAddress;

    //   上传图片url接口
    //    file.transferTo(上传的文件保存到的磁盘文件的File对象)); --实现文件的上传
    @RequestMapping("/img-upload")
    @CrossOrigin//表示此接口允许被跨域请求
    public Result uploadImage(MultipartFile file) throws IOException {
        log.info("uploadAddress - " + uploadAddress);
//      实现文件的上传
//      拿到图片上传到的目录路径的file对象 - classpath:static/img/upload 图片要上传到的路径
        File uploadDirFile = ResourceUtils.getFile(uploadAddress);

//        String file1 = Objects.requireNonNull(this.getClass().getClassLoader().getResource("static/image/upload/")).getFile();
//        System.out.println(file1);

//      我们之前封装目录路径的方式如下所示
//      File f = new File(uploadAddress);
//      现在我们并不能这么封装，因为这个路径是特殊的类路径的路径，我们需要一个特定的工具类进行解析

//      拿到图片上传到的目录路径的磁盘路径
        String uploadDirPath = uploadDirFile.getAbsolutePath();
        log.info("uploadDirPath - " + uploadDirPath);

//      拿到我们上传的图片的名称
        String originalFilename = file.getOriginalFilename();
//      拿到上传的文件要保存到的磁盘文件的路径
        String uploadFilePath = uploadDirPath + "/" + originalFilename;
//      File对象表示文件保存到的那个位置
        file.transferTo(new File(uploadFilePath));


        return Result.ok("图片上传成功！");

    }

    @Autowired
    private TokenUtils tokenUtils;

    //   添加商品的URL接口
    @RequestMapping("/product-add")
    public Result addProduct(@RequestBody Product product, @RequestHeader("Token") String token) {
//      拿到当前登录的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        product.setCreateBy(currentUser.getUserId());

        return productService.addProduct(product);
    }

    //  修改商品上下架状态
    @RequestMapping("/state-change")
    public Result changeProductState(@RequestBody Product product) {
        return productService.updateStateByPid(product);
    }

    //  删除单个商品
    @RequestMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProductByIds(Arrays.asList(productId));
    }

    //  批量删除商品
    @RequestMapping("/product-list-delete")
    public Result deleteProductList(@RequestBody List<Integer> productIdList) {
        return productService.deleteProductByIds(productIdList);
    }

//   修改商品信息
    @RequestMapping("/product-update")
    public Result updateProduct(@RequestBody Product product ,@RequestHeader("Token") String token){
        //拿到当前登录的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        product.setCreateBy(currentUser.getUserId());

        return productService.setProductById(product);
    }

}