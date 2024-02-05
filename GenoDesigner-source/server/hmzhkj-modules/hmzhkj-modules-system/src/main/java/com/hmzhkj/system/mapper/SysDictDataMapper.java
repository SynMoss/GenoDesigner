package com.hmzhkj.system.mapper;

import java.util.List;

import com.hmzhkj.system.domain.SysDictData;
import org.apache.ibatis.annotations.Param;


public interface SysDictDataMapper
{

    List<SysDictData> selectDictDataList(SysDictData dictData);


    List<SysDictData> selectDictDataByType(String dictType);


    String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);


    SysDictData selectDictDataById(String dictCode);


    int countDictDataByType(String dictType);


    int deleteDictDataById(String dictCode);


    int deleteDictDataByIds(String[] dictCodes);


    int insertDictData(SysDictData dictData);


    int updateDictData(SysDictData dictData);


    int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
