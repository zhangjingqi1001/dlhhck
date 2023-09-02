package com.pn;

import com.pn.entity.Auth;
import com.pn.entity.Brand;
import com.pn.mapper.AuthMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WarehouseApplicationTests {

    @Autowired
    private AuthMapper authMapper;

    @Test
    void testCheckScore() {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("score",60);

        authMapper.checkScore(paramMap);

        Object result = paramMap.get("result");

        System.out.println(result);
    }

    @Test
    void testCheckScoreAndGetAuth() {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("score",60);

        List<Auth> authList = authMapper.checkScoreAndGetAuth(paramMap);

        Object result = paramMap.get("result");

        System.out.println("result"+result);
        System.out.println("authList"+authList);

    }

    @Test
    void testCheckScoreAndGetBrand() {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("score",60);


        List<Brand> brands = authMapper.checkScoreAndGetBrand(paramMap);
        Object result = paramMap.get("result");

        System.out.println("result"+result);

        System.out.println("brands"+brands);

    }

}
