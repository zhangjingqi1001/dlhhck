package com.pn.mapper;

import com.pn.entity.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
//  查询所有仓库的方法
    public List<Store> findAllStore();

}
