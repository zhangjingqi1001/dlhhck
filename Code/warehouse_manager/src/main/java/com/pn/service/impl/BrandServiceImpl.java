package com.pn.service.impl;

import com.pn.entity.Brand;
import com.pn.mapper.BrandMapper;
import com.pn.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "com.pn.service.impl.BrandServiceImpl")
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Cacheable(key = "'all:brand'")
    @Override
    public List<Brand> queryAllBrand() {
        return brandMapper.findAllBrand();
    }
}
