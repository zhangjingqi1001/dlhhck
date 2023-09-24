package com.pn.mapper;

import com.pn.entity.InStore;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InStoreMapper {

    //添加入库单的方法
    public int insertInStore(InStore inStore);

//  分页查询的方法
//  查询入库单行数的方法
    public Integer findInStoreCount(@Param("inStore")InStore inStore);

//  分页查询入库单的方法
    public List<InStore> findInStorePage(@Param("page") Page page,@Param("inStore") InStore inStore);

//  根据id修改入库单状态为已入库的方法
    public int  setIsInById(@Param("isStoreId") Integer isStoreId);


}
