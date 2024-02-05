package com.hmzhkj.gene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmzhkj.gene.domain.DesignTBExternalPaccout;

import java.util.List;

 
public interface DesignTBExternalPaccoutService extends IService<DesignTBExternalPaccout> {
    List<DesignTBExternalPaccout> selectDesignTBExternalPaccoutList(DesignTBExternalPaccout designTBExternalPaccout);

    int insertDesignTBExternalPaccout(DesignTBExternalPaccout designTBExternalPaccout);

    int unBindDesignTBExternalPaccout(String id);

    int bindDesignTBExternalPaccout(String id);

    int removeExternalPaccout(String id);

    int updateExternalPaccout(DesignTBExternalPaccout designTBExternalPaccout);

    Boolean hasBindComponent();
}
