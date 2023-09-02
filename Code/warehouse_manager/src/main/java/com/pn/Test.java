package com.pn;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        GetIdentityIdTestDto dto = new GetIdentityIdTestDto("111","222",new Date());
        System.out.println(new Date());
        String toJSONString = JSONObject.toJSONString(dto);

        System.out.println(toJSONString);

        System.out.println(dto.getDate());

    }
}
