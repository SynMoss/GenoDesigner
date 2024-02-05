package com.hmzhkj.gene.controller;

import com.alibaba.fastjson.JSONObject;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/component")
public class ComponentController extends BaseController {

    @Autowired
    private ComponentService componentService;
    @Autowired
    RedisService redisService;

     
    @GetMapping("/user/getToken")
    public String getToken(){
        String token = componentService.getToken();
        return token;
    }

    @PutMapping("/uuid")
    public AjaxResult destroyUUID(String uuid){
        String redisKey = "Component_Flag:" + SecurityUtils.getUserId()+":"+uuid;
        return toAjax(redisService.deleteObject(redisKey));
    }

     
    @PostMapping ("/user/verifyToken")
    public AjaxResult verifyToken(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");
        JSONObject jsonObject = componentService.verifyToken(username, password);
        String msg = jsonObject.toJSONString();
        return jsonObject.getBoolean("success")?AjaxResult.success(msg):AjaxResult.error(msg);
    }

     
    @GetMapping("/list")
    public TableDataInfo getList(String input,String type , Integer pageNum,Integer pageSize,String uuid){
        List<JSONObject> list = componentService.getList(input,type,uuid);
                 List<JSONObject> subList = list.stream().skip((pageNum-1)*pageSize).limit(pageSize).
                collect(Collectors.toList());
        TableDataInfo tableDataInfo = getDataTableCustom(subList, Long.valueOf(list.size()));
        return tableDataInfo;
    }

          
     
    @GetMapping("/info/{code}/{uuid}")
    public AjaxResult getComponentByCode(@PathVariable String code,@PathVariable  String uuid) {
        return AjaxResult.success(componentService.getComponentByCode(code,uuid));
    }

}
