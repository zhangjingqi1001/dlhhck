package com.pn.service.impl;

import com.pn.entity.Product;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
