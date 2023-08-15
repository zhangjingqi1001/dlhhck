package com.pn.mapper;

import com.pn.entity.Supply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplyMapper {
//  查询所有供应商的方法
    public List<Supply> findAllSupply();
}
