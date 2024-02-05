package com.hmzhkj.gene.model;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConvertHistoryParam {
    private String config;
    private String gb;
    private String outdir;

    public ConvertHistoryParam(String config, String gb, String outdir) {
        this.config = config;
        this.gb = gb;
        this.outdir = outdir;
    }

    public ConvertHistoryParam() {
    }
    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }
}
