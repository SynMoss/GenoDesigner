package com.hmzhkj.gene.mapper;

import com.hmzhkj.gene.domain.SequenceOperate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SequenceOperateMapper {
    List<SequenceOperate> list(SequenceOperate item);
    SequenceOperate getLatest(SequenceOperate item);
    SequenceOperate getRollback(SequenceOperate item);
    List<Long> listId(SequenceOperate item);
    List<SequenceOperate> listRollback(SequenceOperate item);
    int saveList(List<SequenceOperate> list);
    int save(SequenceOperate item);
    Integer countMaxOrder(Long sequenceId);
    int removeBySequenceIdList(List<Long> list);
    int removeByIdList( @Param("sequenceId") Long sequenceId, @Param("list") List<Long> list);
}
