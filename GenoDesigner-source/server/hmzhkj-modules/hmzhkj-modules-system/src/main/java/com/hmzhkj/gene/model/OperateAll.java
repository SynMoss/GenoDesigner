package com.hmzhkj.gene.model;

import com.hmzhkj.gene.domain.SequenceOperate;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
@Data
@Accessors(chain = true)
public class OperateAll {
    private Long sequenceId;
     
    private List<SequenceOperate> saveList;
     
    private List<SequenceOperate> delList;

    public List<SequenceOperate> getSaveList() {
        return saveList==null?new ArrayList<>():saveList;
    }

    public List<SequenceOperate> getDelList() {
        return delList==null?new ArrayList<>():delList;
    }
}
