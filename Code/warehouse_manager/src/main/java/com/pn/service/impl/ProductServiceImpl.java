package com.pn.service.impl;

import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page queryProductPage(Page page, Product product) {
//      查询商品行数
        Integer count = productMapper.findProductRowCount(product);
        page.setTotalNum(count);
        page.setLimitIndex(page.getLimitIndex());
        page.setPageCount(page.getPageCount());
//      分页查询商品
        List<Product> productPage = productMapper.findProductPage(page, product);

        page.setResultList(productPage);
        return page;
    }

    @Value("${file.access-path}")
    private String imageURL; ///img/upload/

    //  添加商品的业务方法
    @Override
    public Result addProduct(Product product) {
//      先判断商品的型号是否已存在
        Product prct = productMapper.findProductByNum(product.getProductNum());

        if (prct != null) {
            return Result.err(501, "商品型号已经存在");
        }

//      设定图片的访问地址(product.getImgs()前端传输过来的只有图片的名字)
        product.setImgs(imageURL + product.getImgs());
//      添加商品
        int success = productMapper.insertProduct(product);

        if (success > 0) {
            return Result.ok("添加商品成功");
        }

        return Result.err(501, "添加商品失败");
    }

    @Override
    public Result updateStateByPid(Product product) {
        int success = productMapper.setStateByPid(product.getProductId(), product.getUpDownState());
        return success > 0 ? Result.ok("商品上下架状态修改成功") : Result.err(501, "商品上下架状态修改失败");
    }

    @Override
    public Result deleteProductByIds(List<Integer> productIdList) {
        int success = productMapper.removeProductByIds(productIdList);
        return success > 0 ? Result.ok("商品删除成功") : Result.err(501, "商品删除失败");
    }

    @Override
    public Result setProductById(Product product) {
//      首先看一下型号(根据型号获取product对象)
        Product prod = productMapper.findProductByNum(product.getProductNum());

//      判断型号是否改动
        if (prod != null && !prod.getProductId().equals(product.getProductId())) {
//          表示商品的型号被修改了，并且修改后的型号已经存在
            return Result.err(501, "修改后的型号已经存在");
        }

//      处理突图片，判断图片是否被修改(如果图片被修改了，则这个参数是一个图片的名称，反之是一个完整的文件路径)
        String imgs = product.getImgs();
        if (!imgs.contains(imageURL)){
//          说明图片被修改过了,product.getImgs()的值只是图片的名称
            product.setImgs(imageURL+product.getImgs());
        }

//      修改商品信息
        int success = productMapper.setProductById(product);

        return success > 0 ? Result.ok("商品修改成功") : Result.err(501, "商品修改失败");
    }

}
