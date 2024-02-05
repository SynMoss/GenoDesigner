package com.hmzhkj.gene.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hmzhkj.common.core.utils.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Sequence {
    private String name;
    private List<Feature> features;
    private String sequence;
    private String gbDivision;
    private String date;
    private Boolean circular;
    private String sequenceTypeFromLocus;
    private List<String> extraLines;
    private String keyWords;
    private Long id;
    private String programmeId;
     
    private String fileName;
     
    private String sourceFileName;
    private String source;
    @TableField(exist = false)
    private String sourceFileNameShow;
     
    private Integer isValid;
     
    private Integer isDel;
    public String getSourceFileNameShow(){
        if(StringUtils.isNotEmpty(sourceFileName)){
            String[] strs = sourceFileName.split(",");
            StringBuilder sb = new StringBuilder();
            for (String str : strs) {
                int index = str.indexOf("-");
                if(index>-1){
                    sb.append(str.substring(index+1));
                }else{
                    sb.append(str);
                }
                sb.append(", ");
            }
            sb.delete(sb.length()-2,sb.length()-1);
            return sb.toString();
        }
        return "";
    }
    private Integer bpLength;
         private Integer start;
         private Integer end;
    private StringBuilder sequenceBuilder;
    private Boolean isProtein;

    private Map<String,List<Feature>> sourceFeature;
    private String[] sourceFeatureKeys;
    private Map<String,Integer> selection;
         private List<Map<String,Integer>> searchList;
    private Integer featureLimits;
    private Integer sequenceLengthToShow;
}
