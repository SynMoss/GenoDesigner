package com.hmzhkj.gene.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.util.List;

 
public class LocationsTypeHandle extends FastjsonTypeHandler {
    private final Class<? extends Object> type;

    public LocationsTypeHandle(Class<?> type) {
        super(type);
        this.type = type;
    }

    @Override
    protected List<Location> parse(String json) {
        return JSON.parseArray(json, Location.class);
    }

    @Override
    protected String toJson(Object obj) {
        return super.toJson(obj);
    }
}
