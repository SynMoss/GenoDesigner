package com.hmzhkj.system.service.impl;

import java.util.List;

import com.hmzhkj.system.domain.SysDictData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.framework.utils.DictUtils;
import com.hmzhkj.system.mapper.SysDictDataMapper;
import com.hmzhkj.system.service.ISysDictDataService;

 
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl implements ISysDictDataService
{
    private final SysDictDataMapper dictDataMapper;

     
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        return dictDataMapper.selectDictDataList(dictData);
    }

     
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

     
    @Override
    public SysDictData selectDictDataById(String dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

     
    @Override
    public void deleteDictDataByIds(String[] dictCodes)
    {
        for (String dictCode : dictCodes)
        {
            SysDictData data = selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

     
    @Override
    public int insertDictData(SysDictData data)
    {
        data.genId();
        int row = dictDataMapper.insertDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

     
    @Override
    public int updateDictData(SysDictData data)
    {
        int row = dictDataMapper.updateDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }
}
