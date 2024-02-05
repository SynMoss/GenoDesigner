package com.hmzhkj.system.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.hmzhkj.system.domain.SysDictData;
import com.hmzhkj.system.domain.SysDictType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.utils.DictUtils;
import com.hmzhkj.system.mapper.SysDictDataMapper;
import com.hmzhkj.system.mapper.SysDictTypeMapper;
import com.hmzhkj.system.service.ISysDictTypeService;


@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl implements ISysDictTypeService
{
    private final SysDictTypeMapper dictTypeMapper;

    private final SysDictDataMapper dictDataMapper;


    @PostConstruct
    public void init()
    {
        loadingDictCache();
    }


    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType)
    {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public List selectDictListByIds(String[] ids) {
        List<SysDictType> sysDictTypes = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SysDictType sysDictType = dictTypeMapper.selectDictTypeById(ids[i]);
            sysDictTypes.add(sysDictType);
        }
        return sysDictTypes;
    }


    @Override
    public List<SysDictType> selectDictTypeAll()
    {
        return dictTypeMapper.selectDictTypeAll();
    }


    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if (StringUtils.isNotEmpty(dictDatas))
        {
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (StringUtils.isNotEmpty(dictDatas))
        {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }


    @Override
    public SysDictType selectDictTypeById(String dictId)
    {
        return dictTypeMapper.selectDictTypeById(dictId);
    }


    @Override
    public SysDictType selectDictTypeByType(String dictType)
    {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }


    @Override
    public void deleteDictTypeByIds(String[] dictIds)
    {
        for (String dictId : dictIds)
        {
            SysDictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0)
            {
                throw new ServiceException(String.format("%1$s assigned, cannot be deleted", dictType.getDictName()));
            }
            dictTypeMapper.deleteDictTypeById(dictId);
            DictUtils.removeDictCache(dictType.getDictType());
        }
    }


    @Override
    public void loadingDictCache()
    {
        SysDictData dictData = new SysDictData();
        dictData.setStatus("0");
        Map<String, List<SysDictData>> dictDataMap = dictDataMapper.selectDictDataList(dictData).stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        for (Map.Entry<String, List<SysDictData>> entry : dictDataMap.entrySet())
        {
            DictUtils.setDictCache(entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(SysDictData::getDictSort)).collect(Collectors.toList()));
        }
    }


    @Override
    public void clearDictCache()
    {
        DictUtils.clearDictCache();
    }


    @Override
    public void resetDictCache()
    {
        clearDictCache();
        loadingDictCache();
    }


    @Override
    public int insertDictType(SysDictType dict)
    {
        dict.genId();
        int row = dictTypeMapper.insertDictType(dict);
        if (row > 0)
        {
            DictUtils.setDictCache(dict.getDictType(), null);
        }
        return row;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDictType(SysDictType dict)
    {
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        int row = dictTypeMapper.updateDictType(dict);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dict.getDictType());
            DictUtils.setDictCache(dict.getDictType(), dictDatas);
        }
        return row;
    }


    @Override
    public String checkDictTypeUnique(SysDictType dict)
    {
        SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && !dictType.getDictId().equals(dict.getDictId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
