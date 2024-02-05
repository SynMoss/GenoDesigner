package com.hmzhkj.gene.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ComponentService {

         String getToken();

    JSONObject verifyToken(String username, String password);
         JSONObject getComponentByCode(String code,String uuid);


    List<JSONObject> getList(String input, String type, String uuid);
}
