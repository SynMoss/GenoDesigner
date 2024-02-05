package com.hmzhkj.gene.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class SequenceOperate {
    private Long sequenceId;
    private Long id;
    private Integer start;
    private Integer end;
    private Integer order;
    private String preSequence;
    private String sequence;
    private String sequenceName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date createTime;
    private String createBy;
    private String createByName;
    private String type;
    private Integer strand;
    public String getType(){
        if(StringUtils.isNotEmpty(type)){
            return type.toUpperCase();
        }
        return type;
    }
    private Boolean needSetPreSequence;
    private Boolean canRollback;
     
    private Integer isDel;
    private String sourcePath;
    private List<String> featureTypeList;
    private String errExcelPath;
    private List<Feature> features;
}
