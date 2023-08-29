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

}
