package com.hmzhkj.gene.model;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class CutSequenceParam {
    private String writeFileFolder;
    private String readFilePath;

    public CutSequenceParam(String writeFileFolder, String readFilePath) {
        this.writeFileFolder = writeFileFolder;
        this.readFilePath = readFilePath;
    }
    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }
}
