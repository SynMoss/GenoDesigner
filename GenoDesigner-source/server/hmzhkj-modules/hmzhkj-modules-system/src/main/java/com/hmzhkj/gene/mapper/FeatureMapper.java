package com.hmzhkj.gene.mapper;

import com.hmzhkj.gene.model.FeatureRepeatCondition;
import com.hmzhkj.gene.domain.Feature;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeatureMapper {
    int saveList(List<Feature> list);
    int save(Feature feature);
    int removeBySequenceIdList(@Param("list") List<Long> list, @Param("isTemporary") Boolean isTemporary);


    int updateOperateIdDel(@Param("sequenceId") Long sequenceId,@Param("cutOperateId") Long cutOperateId);


    int removeByOperateId(@Param("sequenceId") Long sequenceId,@Param("operateId") Long operateId);
    int removeByIdList(@Param("sequenceId") Long sequenceId,@Param("idList")List<Long> idList);


    int updateDelById(Feature feature);


    int updateByOperateIdUnDel(@Param("sequenceId") Long sequenceId,@Param("cutOperateIdList")List<Long> cutOperateIdList);
    Long getMaxOperateId(@Param("sequenceId") Long sequenceId);
    List<Feature> list(Feature feature);
    Feature getById(Feature feature);
    List<Feature> listBySequenceId(Long sequenceId);
    List<String> listSource(Long sequenceId);
    int update(Feature feature);
    int saveCloneList(List<Feature> list);


    int removeRepeat(Feature feature);
    int removeByType(Feature feature);

    List<Feature> listRepeat(FeatureRepeatCondition featureRepeatCondition);

    List<String> listType(Feature feature);
    List<String> listTypeByProgrammeId(String programmeId);


    Long getOneRemainId(Feature feature);
}
