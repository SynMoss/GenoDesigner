package com.hmzhkj.gene.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.DesignTBExternalPaccout;
import com.hmzhkj.gene.mapper.DesignTBExternalPaccoutMapper;
import com.hmzhkj.system.domain.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class DesignService {
    private final RestTemplate restTemplate;
    private final RedisService redisService;
    private final DesignTBExternalPaccoutMapper designTBExternalPaccoutMapper;

    @Value("${url.hmzhkj-design}")
    private String designUrl;

    public R<Map> designLogin(String username,String password){
        MultiValueMap<String, String> loginParams = new LinkedMultiValueMap<>();
        loginParams.add("username", username);
        loginParams.add("password", password);
        R<Map> r = restTemplate.postForObject(designUrl+"/externalApi/login",
                loginParams,
                R.class);
        return r;
    }


    private HttpHeaders getHeaders(){
        String designUserId = redisService.getCacheObject("get_design_user:" + SecurityUtils.getUserId());
        R<Map> cache = redisService.getCacheObject("design_token:" + designUserId);
        String token = (String) cache.getData().get("access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);
        return headers;
    }

    public R<String> insertDesignProject(String projectName, String projectDescription) {
        MultiValueMap<String, String> loginParams = new LinkedMultiValueMap<>();
        loginParams.add("projectName", projectName);
        loginParams.add("projectDescription", projectDescription);
        HttpHeaders headers = getHeaders();

        HttpEntity<Map> httpEntity = new HttpEntity<>(loginParams,headers);
        R<String> r = restTemplate.exchange(designUrl+"/externalApi/insertProject",
                HttpMethod.POST,
                httpEntity,
                R.class).getBody();

        if (r.getCode()==401){
            redisService.deleteObject("get_design_user:"+SecurityUtils.getUserId());
            getDesignToken();
            headers = getHeaders();

            httpEntity = new HttpEntity<>(loginParams,headers);
            r = restTemplate.exchange(designUrl+"/externalApi/insertProject",
                    HttpMethod.POST,
                    httpEntity,
                    R.class).getBody();
        }

        return r;
    }

    public R<String> insertDesign(Map<String, String> design) {
        HttpHeaders headers = getHeaders();

        HttpEntity<Map> httpEntity = new HttpEntity<>(design,headers);
        R<String> r = restTemplate.exchange(designUrl+"/externalApi/insertDesign",
                HttpMethod.POST,
                httpEntity,
                R.class).getBody();

        if (r.getCode()==401){
            redisService.deleteObject("get_design_user:"+SecurityUtils.getUserId());
            getDesignToken();
            headers = getHeaders();

            httpEntity = new HttpEntity<>(design,headers);
            r = restTemplate.exchange(designUrl+"/externalApi/insertDesign",
                    HttpMethod.POST,
                    httpEntity,
                    R.class).getBody();
        }
        return r;
    }


    public R<Map> getDesignToken() {

        QueryWrapper<DesignTBExternalPaccout> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_bind", 1)
                .eq("is_delete", 1)
                .eq("user_id", SecurityUtils.getUserId())
                .eq("external_system_name", "Device design system");
        DesignTBExternalPaccout designTBExternalPaccout = designTBExternalPaccoutMapper.selectOne(queryWrapper);
        if(designTBExternalPaccout == null){
            throw new ServiceException("Currently unbound device design account!");
        }

        String designUserId = redisService.getCacheObject("get_design_user:"+SecurityUtils.getUserId());
        R<Map> cacheObject = null;
        if(StringUtils.isNotEmpty(designUserId)){
            cacheObject = redisService.getCacheObject("design_token:"+designUserId);
        }

        if(cacheObject == null){
            String username = designTBExternalPaccout.getExternalSystemUserName();
            String password = designTBExternalPaccout.getExternalSystemPassword();
            R<Map> r = designLogin(username, password);
            Map<String,Object> data = r.getData();
            SysUser sysUser = JSONObject.parseObject(data.get("userInfo").toString(), SysUser.class);
            designUserId = sysUser.getUserId();
            redisService.setCacheObject("get_design_user:"+SecurityUtils.getUserId(),designUserId, 60*60*10L, TimeUnit.SECONDS);
            redisService.setCacheObject("design_token:"+designUserId,r, 60*60*10L, TimeUnit.SECONDS);
            return r;
        }
        return cacheObject;
    }
}
