package com.hmzhkj.system.service;

import com.hmzhkj.system.domain.SysDictData;

import java.util.List;


public interface ISysDictDataService
{

    public List<SysDictData> selectDictDataList(SysDictData dictData);


    public String selectDictLabel(String dictType, String dictValue);


    public SysDictData selectDictDataById(String dictCode);


    public void deleteDictDataByIds(String[] dictCodes);


    public int insertDictData(SysDictData dictData);


    public int updateDictData(SysDictData dictData);
}
