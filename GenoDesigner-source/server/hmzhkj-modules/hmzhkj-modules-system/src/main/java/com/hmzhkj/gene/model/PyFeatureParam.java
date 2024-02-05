package com.hmzhkj.gene.model;

import com.alibaba.fastjson2.JSONObject;
import com.hmzhkj.gene.util.SeqUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class PyFeatureParam {
    private String path;
    private List<String> typeList;
    private Map<String,Integer[]> typeRemainMap;
    private Map<String,Object> featureMap;
    private String featureType;
    private String featureName;
    private String sequence;
    private Long sequenceId;
    private List<String> pathList;
    private String codonReplace;
    private List<String> codonDelList;
    private String excelPath;
    private String sequenceName;
    private Long operateId;
    private Integer strand;
    public PyFeatureParam(String path, List<String> typeList, Map<String,Integer[]> typeRemainMap) {
        this.path = path;
        this.typeList = typeList;
        this.typeRemainMap = typeRemainMap;
    }
    public PyFeatureParam(String path, Map<String,Integer[]> typeRemainMap) {
        this.path = path;
        this.typeRemainMap = typeRemainMap;
    }
    public PyFeatureParam() {
    }
    public void sequenceToUpperCase(){
        if(sequence!=null){
            sequence = sequence.toUpperCase();
        }
    }
    public void reverseComplementSequence(){
        if(sequence!=null){
            sequence = sequence.toUpperCase();
            sequence = SeqUtil.reverseComplement(sequence);
        }
    }
    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }
}
