package com.pn.mapper;

import com.pn.entity.Brand;
import com.pn.entity.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {

//  查询所有品牌的方法
    public List<Brand> findAllBrand();

}
