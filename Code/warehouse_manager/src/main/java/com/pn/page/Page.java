package com.pn.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 分页信息实体类:
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Page {

    //当前页码
    private Integer pageNum;

    //每页显示行数
    private Integer pageSize;

    //总行数
    private Integer totalNum;

    //总页数
    private Integer pageCount;

    //limit函数参数一每页起始行
    private Integer limitIndex;

    //存储当前页查询到的数据的List<?>集合
    private List<?> resultList;

    //计算总页数
    public Integer getPageCount() {
        log.info("计算总页数 "+( (totalNum%pageSize==0) ? totalNum/pageSize : totalNum/pageSize+1));
        return (totalNum%pageSize==0) ? totalNum/pageSize : totalNum/pageSize+1;
    }

    //计算limit函数参数一每页起始行
    public Integer getLimitIndex() {
        log.info("计算limit函数参数一每页起始行  "+( pageSize * (pageNum-1)));
        return pageSize * (pageNum-1);
    }
}
