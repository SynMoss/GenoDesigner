package com.hmzhkj.system.service;

import com.hmzhkj.system.domain.SysDictData;
import com.hmzhkj.system.domain.SysDictType;

import java.util.List;


public interface ISysDictTypeService
{

    public List<SysDictType> selectDictTypeList(SysDictType dictType);


    public List selectDictListByIds(String[] ids);



    public List<SysDictType> selectDictTypeAll();


    public List<SysDictData> selectDictDataByType(String dictType);


    public SysDictType selectDictTypeById(String dictId);


    public SysDictType selectDictTypeByType(String dictType);


    public void deleteDictTypeByIds(String[] dictIds);


    public void loadingDictCache();


    public void clearDictCache();


    public void resetDictCache();


    public int insertDictType(SysDictType dictType);


    public int updateDictType(SysDictType dictType);


    public String checkDictTypeUnique(SysDictType dictType);
}
