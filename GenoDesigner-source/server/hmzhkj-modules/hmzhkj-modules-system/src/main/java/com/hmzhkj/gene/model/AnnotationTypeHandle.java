package com.hmzhkj.gene.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.util.List;

 
public class AnnotationTypeHandle extends FastjsonTypeHandler {
    private final Class<? extends Object> type;

    public AnnotationTypeHandle(Class<?> type) {
        super(type);
        this.type = type;
    }

    @Override
    protected List<Annotation> parse(String json) {
        return JSON.parseArray(json, Annotation.class);
    }

    @Override
    protected String toJson(Object obj) {
        return super.toJson(obj);
    }
}
