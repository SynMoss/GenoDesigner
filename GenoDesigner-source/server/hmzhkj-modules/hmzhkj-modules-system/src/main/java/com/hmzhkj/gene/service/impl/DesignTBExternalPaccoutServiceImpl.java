package com.hmzhkj.gene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.DesignTBExternalPaccout;
import com.hmzhkj.gene.mapper.DesignTBExternalPaccoutMapper;
import com.hmzhkj.gene.service.DesignTBExternalPaccoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

 
@Service
@RequiredArgsConstructor
public class DesignTBExternalPaccoutServiceImpl extends ServiceImpl<DesignTBExternalPaccoutMapper, DesignTBExternalPaccout> implements DesignTBExternalPaccoutService {
    private final DesignTBExternalPaccoutMapper designTBExternalPaccoutMapper;
    @Override
    public List<DesignTBExternalPaccout> selectDesignTBExternalPaccoutList(DesignTBExternalPaccout designTBExternalPaccout) {
        String userId = SecurityUtils.getUserId();
        QueryWrapper<DesignTBExternalPaccout> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("is_delete",1).orderByDesc("create_time");
        String externalSystemName = designTBExternalPaccout.getExternalSystemName();
        if(StringUtils.isNotEmpty(externalSystemName)){
            queryWrapper.like("external_system_name",externalSystemName);
        }
        return designTBExternalPaccoutMapper.selectList(queryWrapper);
    }

    @Override
    public int insertDesignTBExternalPaccout(DesignTBExternalPaccout designTBExternalPaccout) {
        designTBExternalPaccout.setIsBind(1).setIsDelete(1).setUserId(SecurityUtils.getUserId()).setBindTime(new Date());
                          return designTBExternalPaccoutMapper.insert(designTBExternalPaccout);
    }

    @Override
    public int unBindDesignTBExternalPaccout(String id) {
        UpdateWrapper<DesignTBExternalPaccout> updateWrapper = new UpdateWrapper<>();
        Date date = null;
        updateWrapper.eq("id",id).set("is_bind",0).set("bind_time",date);
        return designTBExternalPaccoutMapper.update(null,updateWrapper);
    }
    @Override
    public int bindDesignTBExternalPaccout(String id) {
        UpdateWrapper<DesignTBExternalPaccout> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("is_bind",1).set("bind_time",new Date());
        return designTBExternalPaccoutMapper.update(null,updateWrapper);
    }

    @Override
    public int removeExternalPaccout(String id) {
        return designTBExternalPaccoutMapper.deleteById(id);
    }

    @Override
    public int updateExternalPaccout(DesignTBExternalPaccout designTBExternalPaccout) {
        return designTBExternalPaccoutMapper.updateById(designTBExternalPaccout);
    }

    @Override
    public Boolean hasBindComponent() {
        QueryWrapper<DesignTBExternalPaccout> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_bind", 1)
                .eq("is_delete", 1)
                .eq("user_id", SecurityUtils.getUserId())
                .eq("external_system_name", "Component library system");
        DesignTBExternalPaccout designTBExternalPaccout = designTBExternalPaccoutMapper.selectOne(queryWrapper);
        return !(designTBExternalPaccout==null);
    }
}
