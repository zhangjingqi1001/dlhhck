package com.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//mapper接口扫描器,然后会自动为Mapper接口创建代理对象并加入到IOC容器
@MapperScan(basePackages = "com.pn.mapper")
@SpringBootApplication
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

}
