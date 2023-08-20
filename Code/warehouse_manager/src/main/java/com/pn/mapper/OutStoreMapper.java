package com.pn.mapper;

import com.pn.entity.OutStore;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutStoreMapper {

    //添加出库单的方法
    public int insertOutStore(OutStore outStore);

}
