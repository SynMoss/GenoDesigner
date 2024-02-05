package com.hmzhkj.gene.model;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
@Data
@Accessors(chain = true)
public class ImportConfigParam {
    private String chr;
    private String featureType;
    private String featureName;
    private String seq;
    private Integer strand;
    private String oriSeq;
    private Integer start;
    private Integer end;
    private String editType;
    private Integer featureStart;
    private Integer featureEnd;
    public String getOriSeq(){
        if(oriSeq!=null){
            return oriSeq.toUpperCase();
        }
        return null;
    }
    public String getSeq(){
        if(seq!=null){
            return seq.toUpperCase();
        }
        return null;
    }
}
