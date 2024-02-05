package com.hmzhkj.system.mapper;

import com.hmzhkj.system.domain.SysDictType;

import java.util.List;


public interface SysDictTypeMapper
{

    List<SysDictType> selectDictTypeList(SysDictType dictType);


    List<SysDictType> selectDictTypeAll();


    SysDictType selectDictTypeById(String dictId);


    SysDictType selectDictTypeByType(String dictType);


    int deleteDictTypeById(String dictId);


    int deleteDictTypeByIds(String[] dictIds);


    int insertDictType(SysDictType dictType);


    int updateDictType(SysDictType dictType);


    SysDictType checkDictTypeUnique(String dictType);
}
