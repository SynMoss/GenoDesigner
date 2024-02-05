package com.hmzhkj.gene.service.impl;


import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.DesignTBExternalPaccout;
import com.hmzhkj.gene.mapper.DesignTBExternalPaccoutMapper;
import com.hmzhkj.gene.service.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

 
@Service
@PropertySource("classpath:component.properties")
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {
    private final RestTemplate restTemplate;

    private final RedisService redisService;
    private final DesignTBExternalPaccoutMapper designTBExternalPaccoutMapper;

    @Value("${component.baseUrl}")
    private String baseUrl;
    @Value("${component.email}")
    private String adminEmail;
    @Value("${component.password}")
    private String adminPassword;
    @Value("${component.folderId}")
    private String folderId;

    @Value("${sequence.url}")
    private String sequenceUrl;

    private String authorizationAdmin;

     
    public HttpHeaders createHeaders(String authorization,Boolean isJson) {
        return new HttpHeaders() {
            {
                set("authorization", authorization);
                if(isJson){
                    set("Accept", "application/json");
                    set("Content-Type", "application/json");
                }
            }
        };
    }

     
    public JSONObject getSequence(String url,String partId){
        Map<String,String> param = new HashMap<>();
        param.put("url",url);
        param.put("partId",partId);
        return restTemplate.postForObject(sequenceUrl, param,JSONObject.class);
    }

     
    private DesignTBExternalPaccout getExternalAccount(){
        QueryWrapper<DesignTBExternalPaccout> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_bind", 1)
                .eq("is_delete", 1)
                .eq("user_id", SecurityUtils.getUserId())
                .eq("external_system_name", "Component library system");
        DesignTBExternalPaccout designTBExternalPaccout = designTBExternalPaccoutMapper.selectOne(queryWrapper);
        if(designTBExternalPaccout == null){
            throw new ServiceException("The current user is not bound to a component library account");
        }
        return designTBExternalPaccout;
    }

     
    public JSONObject getResult(String api,HttpMethod method,Map params,String uuid){
        String authorization = "";

                 String redisKey = "Component_Flag:" + SecurityUtils.getUserId()+":"+uuid;
        Integer componentFlag = 0;
        if(uuid==null){
            componentFlag = 1;
        }else{
            redisService.getCacheObject(redisKey);
        }
                 if( componentFlag==0){
            if(authorizationAdmin == null){
                Object componentTokenAdmin = redisService.getCacheObject("Component_Token_Admin");
                                 if(componentTokenAdmin == null || "".equals(componentTokenAdmin)){
                    getAdminToken();
                }else{
                    this.authorizationAdmin= ((Map<String, String>) componentTokenAdmin).get("authorization");
                }
            }
            authorization = this.authorizationAdmin;
        }else{
            Object componentToken = redisService.getCacheObject("Component_Token:" + SecurityUtils.getUserId());
            if(componentToken == null || "".equals(componentToken) ){
                authorization = getToken();
            }else {
                authorization= ((Map<String, String>) componentToken).get("authorization");
            }
        }

                 String url = baseUrl+api;
        HttpEntity<Map> httpEntity = new HttpEntity<>(params, createHeaders(authorization,method.equals(HttpMethod.POST)));

        JSONObject body = null;
        try{
            body = restTemplate.exchange(url, method, httpEntity, JSONObject.class).getBody();
        }catch (Exception e){
            String message = e.getMessage();
            String code = message.substring(0,message.indexOf(":"));
            String resultBody = message.substring(message.indexOf(":")+1).trim();
            resultBody = resultBody.substring(1,resultBody.length()-1);
            body = JSONObject.parseObject(resultBody);
        }
        if (body == null) {
            throw new ServiceException("Data acquisition failed!");
        }
                 if(body.getInteger("code") == HttpStatus.HTTP_UNAUTHORIZED){
            if(api.startsWith("folder/getComponentListByFolder")){
                getAdminToken();
                authorization = this.authorizationAdmin;
            }else{
                authorization = getToken();
            }
                         httpEntity = new HttpEntity<>(params, createHeaders(authorization,method.equals(HttpMethod.POST)));

            try{
                body = restTemplate.exchange(url, method, httpEntity, JSONObject.class).getBody();
            }catch (Exception e){
                String message = e.getMessage();
                throw new ServiceException(message);
            }
        }
        return body;
    }

     
    private void getAdminToken(){
        String api = "user/getToken";
        String url = baseUrl+api;
                 Map<String ,String> admin = new HashMap<>();
        admin.put("email",adminEmail);
        admin.put("password",adminPassword);
        JSONObject body = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(admin, null), JSONObject.class).getBody();
        Boolean success = body.getBoolean("success");
        if(success){
            Map<String, Object> bodyMap = (Map<String, Object>) body.get("body");
            String verifyKey = "Component_Token_Admin";
            redisService.setCacheObject(verifyKey,bodyMap, 60*60*20L, TimeUnit.SECONDS );
            this.authorizationAdmin = (String) bodyMap.get("authorization");
        }else{
            throw new ServiceException("Failed to obtain component library administrator token!  "+body.getString("message"));
        }
    }

     
    @Override
    public String getToken() {
        String api = "user/getToken";
        String url = baseUrl+api;
                 DesignTBExternalPaccout account = getExternalAccount();
        String email = account.getExternalSystemUserName();
        String password = account.getExternalSystemPassword();
        Map<String ,String> general = new HashMap<>();
        general.put("email",email);
        general.put("password",password);
        JSONObject body = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(general, null), JSONObject.class).getBody();
        Boolean success = body.getBoolean("success");
        if(success){
            Map<String, Object> bodyMap = (Map<String, Object>) body.get("body");
            String verifyKey = "Component_Token:" + SecurityUtils.getUserId();
            redisService.setCacheObject(verifyKey,bodyMap, 60*60*20L, TimeUnit.SECONDS );
        }else{
            throw new ServiceException("Failed to obtain component library token!  "+body.getString("message"));
        }
        return body.getJSONObject("body").getString("authorization");
    }

     
    @Override
    public JSONObject verifyToken(String username, String password) {
        String api = "user/getToken";
        String url = baseUrl+api;
        Map<String ,String> general = new HashMap<>();
        general.put("email",username);
        general.put("password",password);
        JSONObject body = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(general, null), JSONObject.class).getBody();
        return body;
    }

     
    public List<JSONObject> getComponentListByName(String name,String uuid){
        String api = "component/getComponentListByName?name="+name;
        JSONObject result = getResult(api, HttpMethod.GET, null,uuid);
        if(result.getInteger("code") == HttpStatus.HTTP_OK){
            List<JSONObject> list = result.getJSONArray("body").toJavaList(JSONObject.class);
            return getSimpleList(list);
        }else{
            throw new ServiceException("Failed to obtain component information!");
        }
    }

     

    public List<JSONObject> getDefaultList(String uuid){
        String api = "folder/getComponentListByFolder?showAllDetails=true&limit=100&folderId="+folderId;
        JSONObject result = getResult(api, HttpMethod.GET, null,uuid);
        if(result.getInteger("code") == HttpStatus.HTTP_OK){
            List<JSONObject> list = result.getJSONObject("body").getJSONArray("returnDataList").toJavaList(JSONObject.class);
            return getSimpleList(list);
        }else{
            throw new ServiceException("Failed to obtain component information!");
        }
    }

     
    public List<JSONObject> getComponentListByCode(String codes,String uuid) {
        codes.replace("，", ",");
        String[] codeArr = codes.split(",");
        List<JSONObject> resultList = new ArrayList<>();
        for (String code : codeArr) {
            String api = "component/getComponentByCode?code="+code;
            JSONObject result = getResult(api, HttpMethod.GET, null,uuid);
            if(result.getBoolean("success")){
                resultList.add(result.getJSONObject("body"));
            }
        }
        return getSimpleList(resultList);
    }

         @Override
    public List<JSONObject> getList(String input, String type, String uuid) {
        List<JSONObject> list = null;
        String redisKey = "Component_Flag:" + SecurityUtils.getUserId()+":"+uuid;
                 Integer oldFlag = redisService.getCacheObject(redisKey);
        try {
            if (input == null || "".equals(input)) {
                redisService.setCacheObject(redisKey, 0);
                list = getDefaultList(uuid);
            } else {
                redisService.setCacheObject(redisKey, 1);
                if ("name".equals(type)) {
                    list = getComponentListByName(input,uuid);
                } else {
                    list = getComponentListByCode(input,uuid);
                }
            }
        }catch (Exception e){
            redisService.setCacheObject(redisKey, oldFlag);
            System.err.println("Abandoned");
            throw e;
        }
        return list;
    }

     
    public List<JSONObject> getSimpleList(List<JSONObject> list){
        List<JSONObject> result = new ArrayList<>();
        for (JSONObject jsonObject : list) {
            if(jsonObject.getInteger("sequenceFlag") == 1){
                JSONObject json = new JSONObject();
                json.put("code", jsonObject.getString("code"));
                json.put("name", jsonObject.getString("name"));
                JSONObject oveSequence = jsonObject.getJSONObject("oveSequence");
                if(oveSequence != null){
                    json.put("length", oveSequence.getString("length"));
                    json.put("description", oveSequence.getString("description"));
                }
                result.add(json);
            }
        }
        return result;
    }

     
    @Override
    public JSONObject getComponentByCode(String code,String uuid) {
        String api = "component/getComponentByCode?code="+code;
        JSONObject result = getResult(api, HttpMethod.GET, null,uuid);
        JSONObject partInfo = new JSONObject();
        if(result.getBoolean("success")){
            JSONObject body = result.getJSONObject("body");
            if(body.getInteger("sequenceFlag")==0){
                                 throw new ServiceException("This component has no sequence");
            }else{
                partInfo.put("partName", body.getString("name"));
                String partType = body.getString("type");
                partInfo.put("partType", partType);
                JSONObject fieldDetails = body.getJSONObject("fieldDetails");
                String subType = null;
                String description = null;
                partInfo.put("bpLength",body.getJSONObject("oveSequence").getString("length"));
                partInfo.put("content",body.getJSONObject("oveSequence").getString("sequence"));
                partInfo.put("circular",body.getJSONObject("oveSequence").getBoolean("circular"));
                partInfo.put("sourceName",body.getJSONObject("oveSequence").getString("name"));
                partInfo.put("code",code);
                partInfo.put("format","GENBANK");
            }
        }else{
            throw new ServiceException("Interface query failed");
        }
        return partInfo;
    }

}
