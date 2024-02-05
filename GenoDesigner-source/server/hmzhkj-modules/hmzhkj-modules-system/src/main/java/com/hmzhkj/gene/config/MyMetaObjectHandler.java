package com.hmzhkj.gene.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hmzhkj.auth.service.TokenService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    private TokenService tokenService;

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String userId = loginUser.getUserid();
        String nickName = loginUser.getSysUser().getNickName();
        String username = loginUser.getUsername();
        Date date = new Date();
        this.setFieldValByName("createTime", date, metaObject);
        this.setFieldValByName("createStaffNo", userId, metaObject);
                 this.setFieldValByName("createStaffName", username, metaObject);
        this.setFieldValByName("updateTime", date, metaObject);
        this.setFieldValByName("updateStaffNo", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = SecurityUtils.getUserId();
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateStaffNo", userId, metaObject);
    }
}
