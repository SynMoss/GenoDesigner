package com.hmzhkj.gene.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FaGffToGbParam {
    private String fa;
    private String gf;
    private String out;
    private String faName;
    public FaGffToGbParam(String fa, String gf, String out) {
        this.fa = fa;
        this.gf = gf;
        this.out = out;
    }

    public FaGffToGbParam() {
    }
    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
