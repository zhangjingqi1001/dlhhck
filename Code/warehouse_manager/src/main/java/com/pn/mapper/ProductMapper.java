package com.pn.mapper;

import com.pn.entity.Product;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

//  查询商品行数的方法
    public Integer findProductRowCount(Product product);

//  分页查询商品的方法
    public List<Product> findProductPage(@Param("page") Page page, @Param("product") Product product);

}
