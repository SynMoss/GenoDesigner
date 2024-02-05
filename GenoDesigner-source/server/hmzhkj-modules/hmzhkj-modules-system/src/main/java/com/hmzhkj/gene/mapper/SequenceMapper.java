package com.hmzhkj.gene.mapper;

import com.hmzhkj.gene.domain.Sequence;

import java.util.List;

public interface SequenceMapper {
    void save(Sequence sequence);
    int saveList(List<Sequence> list);
    List<Sequence> list(Sequence sequence);

     
    int removeByIdList(List<Long> list);

    Sequence get(Sequence sequence);
    List<Sequence> listById(Long id);
    int update(Sequence sequence);
    int updateValid(Sequence sequence);
     
    List<Sequence> listToDelSequence(Sequence sequence);
}
