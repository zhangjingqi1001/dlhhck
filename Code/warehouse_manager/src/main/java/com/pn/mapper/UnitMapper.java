package com.pn.mapper;

import com.pn.entity.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper {

    //查询所有单位的方法
    public List<Unit> findAllUnit();
}
