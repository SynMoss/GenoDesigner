package com.hmzhkj.gene.domain;

import com.hmzhkj.gene.constant.ImportConfigEnum;
import com.hmzhkj.gene.model.ImportConfigParam;
import com.hmzhkj.gene.model.Location;
import com.hmzhkj.gene.util.GenBankToJson;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Feature {
    private Long id;
    private Long idCopy;
    private List<Long> idList;
    private Integer oldSequenceLength;
    public Long getIdCopy(){
        if(idCopy==null){
            return id;
        }
        return idCopy;
    }
    private Long sequenceId;
    private String programmeId;
    private String operator;
    private Integer start;
    private Integer end;
    private List<Location> locations;
    private Map<String,List<String>> notes;
     
    private Map<String,String> history;
    private String type;
    private Integer strand;
    private String name;
    private String arrowheadType;
    private String sequenceName;
    private String source;
     
    private Boolean isTemporary;
    private Integer sort;
    private Integer sequenceLength;
     
    private Integer isDel;
    private List<String> typeList;
    private Map<String,Integer[]> typeRemainMap;;
    private Integer frontCount;
    private Integer behindCount;
     
    private Long operateId;
     
    private Long cutOperateId;

    public Feature(Long id) {
        this.id = id;
    }

    public Feature() {
    }
    public void nameAppend(String str){
        this.name = this.name+"."+str.toUpperCase();
    }
    public Feature(ImportConfigParam importConfigParam) {
        this.name = importConfigParam.getFeatureName()==null?"Untitled Feature":importConfigParam.getFeatureName();
        this.type = importConfigParam.getFeatureType();
        this.start = importConfigParam.getFeatureStart();
                 this.end = importConfigParam.getFeatureEnd()-1;
        this.strand = importConfigParam.getStrand()==null?1:importConfigParam.getStrand();
        this.locations = new ArrayList<>();
        this.locations.add(new Location(this.start,this.end));
        this.notes = new HashMap<>();
        List<String> a = new ArrayList<>();
        a.add(this.name);
        this.notes.put("locus_tag",a);
    }
    public Feature(Feature feat) {
                 this.name = feat.getName();
        this.start = feat.getStart();
        this.end = feat.getEnd();
        this.id = feat.getId();
        this.sequenceId = feat.getSequenceId();
        this.notes = feat.getNotes();
        this.type = feat.getType();
        this.strand = feat.getStrand();
        this.arrowheadType = feat.getArrowheadType();
        this.sequenceName = feat.getSequenceName();
        this.source = feat.getSource();
        this.isTemporary = feat.getIsTemporary();
        this.sort = feat.getSort();
        this.sequenceLength = feat.getSequenceLength();
    }
     
    public void init(int bpLength){
        if(locations!=null && !locations.isEmpty()){
            this.start = locations.get(0).getStart();
            this.end = locations.get(locations.size()-1).getEnd();
                         for (int i = 0; i < locations.size(); i++) {
                Location location = locations.get(i);
                if(location.getEnd()==bpLength-1 && i!=locations.size()-1&& locations.get(i+1).getStart()==0){
                    location.setEnd(locations.get(i+1).getEnd());
                    locations.remove(i+1);
                    break;
                }
            }
        }
        GenBankToJson.postProcessGenbankFeature(this);
    }
}
