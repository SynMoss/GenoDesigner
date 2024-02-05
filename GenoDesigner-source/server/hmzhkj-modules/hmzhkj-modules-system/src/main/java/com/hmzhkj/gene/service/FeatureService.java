package com.hmzhkj.gene.service;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson2.JSON;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.FileUploadUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.gene.constant.GenbankAnnotationKey;
import com.hmzhkj.gene.constant.OperateEnum;
import com.hmzhkj.gene.domain.Feature;
import com.hmzhkj.gene.domain.Programme;
import com.hmzhkj.gene.domain.Sequence;
import com.hmzhkj.gene.domain.SequenceOperate;
import com.hmzhkj.gene.mapper.FeatureMapper;
import com.hmzhkj.gene.mapper.ProgrammeMapper;
import com.hmzhkj.gene.mapper.SequenceMapper;
import com.hmzhkj.gene.mapper.SequenceOperateMapper;
import com.hmzhkj.gene.model.End3;
import com.hmzhkj.gene.model.FeatureRepeatCondition;
import com.hmzhkj.gene.model.Location;
import com.hmzhkj.gene.model.Range;
import com.hmzhkj.gene.util.GffToFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureService {
    private final FeatureMapper featureMapper;
    private final SequenceMapper sequenceMapper;
    private final RedisService redisService;
    private final SequenceOperateMapper sequenceOperateMapper;
     
    public End3 listFeatureByTypeEnd3(Long sequenceId,List<String> typeList){
        List<Feature> list = featureMapper.list(new Feature().setSequenceId(sequenceId).setTypeList(typeList));
        List<Feature> result = new ArrayList<>(list.size());
        List<Feature> resultA = new ArrayList<>(list.size());
        for (Feature l : list) {
            boolean canAdd = true;
            for (Feature r : result) {
                int a,b;
                if(Integer.valueOf(-1).equals(r.getStrand()) && Integer.valueOf(-1).equals(l.getStrand())){
                    a = r.getStart();
                    b=l.getStart();
                }else if(Integer.valueOf(-1).equals(r.getStrand())){
                    a = r.getStart();
                    b=l.getEnd();
                }else if(Integer.valueOf(-1).equals(l.getStrand())){
                    a = r.getEnd();
                    b = l.getStart();
                }else{
                    a = r.getEnd();
                    b=r.getEnd();
                }
                if(Math.abs(a-b)<3){
                    canAdd = false;
                    break;
                }
            }
            if(canAdd){
                result.add(l);
            }else{
                resultA.add(l);
            }
        }
        End3 end3 = new End3();
        end3.setCanList(result);
        end3.setCanNotList(resultA);
        return end3;
    }
     
    public List<Location> removeFeatureByType(Feature feature){
        if(feature.getTypeList()==null || feature.getTypeList().isEmpty()||feature.getSequenceId()==null){
            throw new ServiceException("Incomplete parameters");
        }
        Sequence sequence = sequenceMapper.get(new Sequence().setId(feature.getSequenceId()));
        sequence.getBpLength();
        Set<Integer> treeSet = new TreeSet<>();
        List<Feature> featureList= featureMapper.list(
                new Feature().setSequenceId(feature.getSequenceId()).setTypeList(feature.getTypeList()));
                 for (Feature feat : featureList) {
            int start=feat.getStart(),end=feat.getEnd();
            if(start>end){
                start = 0;
                end = feat.getStart();
                for (int i = start; i <= end; i++) {
                    treeSet.add(i);
                }
                start = feat.getEnd();
                end = sequence.getBpLength()-1;
            }
            for (int i = start; i <= end; i++) {
                treeSet.add(i);
            }
        }
        List<Integer> arrayList = new ArrayList<>(treeSet);
        List<Location> list = new ArrayList<>();
        Location location = new Location();
        location.setStart(arrayList.get(0));
        list.add(location);
        for (int i = 0; i < arrayList.size()-1; i++) {
            int currentI = arrayList.get(i);
            int nextI = arrayList.get(i+1);
                         if(currentI+1!=nextI){
                list.get(list.size()-1).setEnd(currentI);
                Location l = new Location();
                l.setStart(nextI);
                list.add(l);
            }
        }
        Location end = list.get(list.size()-1);
        end.setEnd(arrayList.get(arrayList.size()-1));
        if(end.getEnd()==sequence.getBpLength()-1&&end.getStart()==0){
            throw new ServiceException("The feature length covers the entire sequence!");
        }
                          return list;
    }

     
    public List<Location> removeOtherFeatureByType(Feature feature){
        if(feature.getTypeList()==null || feature.getTypeList().isEmpty()||feature.getSequenceId()==null
        ||feature.getFrontCount()==null||feature.getBehindCount()==null){
            throw new ServiceException("Incomplete parameters");
        }
        Sequence sequence = sequenceMapper.get(new Sequence().setId(feature.getSequenceId()));
        sequence.getBpLength();
        Set<Integer> treeSet = new TreeSet<>();
        List<Feature> featureList= featureMapper.list(
                new Feature().setSequenceId(feature.getSequenceId()).setTypeList(feature.getTypeList()));
        for (Feature feat : featureList) {
            int start=feat.getStart()-feature.getFrontCount(),end=feat.getEnd()+feature.getBehindCount();
            if(Integer.valueOf(-1).equals(feat.getStrand())){
                start = feat.getStart()-feature.getBehindCount();
                end = feat.getEnd()+feature.getFrontCount();
            }

                         if(feat.getStart()>feat.getEnd() && start<end){
                throw new ServiceException("Retain length to cover the entire sequence!");
            }
            if(start>end){
                start = 0;
                end = feat.getStart();
                for (int i = start; i <= end; i++) {
                    treeSet.add(i);
                }
                start = feat.getEnd()-feature.getFrontCount();
                end = sequence.getBpLength()-1;
            }
            if(start<0){
                start=0;
            }
            if(end>=sequence.getBpLength()){
                end = sequence.getBpLength()-1;
            }
            for (int i = start; i <= end; i++) {
                treeSet.add(i);
            }
        }
        List<Integer> arrayList = new ArrayList<>(treeSet);
        List<Location> list = new ArrayList<>();
        for (int i = 0; i < arrayList.size()-1; i++) {
            int currentI = arrayList.get(i);
            int nextI = arrayList.get(i+1);
            if(currentI+1!=nextI){
                Location l = new Location();
                l.setStart(currentI);
                l.setEnd(nextI-2);
                list.add(l);
            }
        }
        if(arrayList.get(0)>0){
            list.add(0,new Location(0,arrayList.get(0)));
        }
        if(arrayList.get(arrayList.size()-1)< sequence.getBpLength()-1){
            list.add(new Location(arrayList.get(arrayList.size()-1),sequence.getBpLength()-2));
        }
        if(list.isEmpty()){
            throw new ServiceException("Retain length to cover the entire sequence!");
        }
        return list;
    }
    public List<String> listType(Feature feature){
        if(feature.getSequenceId()==null&&feature.getProgrammeId()==null){
            throw new ServiceException("Incomplete parameters");
        }
        if(StringUtils.isNotEmpty(feature.getProgrammeId())){
            return featureMapper.listTypeByProgrammeId(feature.getProgrammeId());
        }
        return featureMapper.listType(feature);
    }

     
    @Transactional(rollbackFor = Exception.class)
    public void rollBack(Long sequenceId, Long operateId) {
                 List<Long> idList = sequenceOperateMapper.listId(new SequenceOperate().setId(operateId).setSequenceId(sequenceId));
                 featureMapper.removeByOperateId(sequenceId,operateId);
                 featureMapper.updateByOperateIdUnDel(sequenceId,idList);
    }
    public AjaxResult saveFeatureList(List<Feature> featureList) {
        if(featureList.isEmpty()){
            return AjaxResult.success(new ArrayList<>());
        }
        for (Feature feature : featureList) {
            dealFeature(feature);
        }
        SequenceOperate operate = sequenceOperateMapper.getLatest(new SequenceOperate().setSequenceId(featureList.get(0).getSequenceId()));
        Long operateId = null;
        if(operate!=null){
            operateId = operate.getId();
        }
        return AjaxResult.success(saveFeatureListOperate(featureList,operateId));
    }
    public int saveFeature(Feature feature) {
        dealFeature(feature);
        return featureMapper.save(feature);
    }
    public List<Long> saveFeatureListOperate(List<Feature> featureList,Long operateId){
        List<Long> idList = new ArrayList<>(featureList.size());
        List<Feature> saveList = new ArrayList<>();
        Iterator<Feature> iterable = featureList.iterator();
        while (iterable.hasNext()){
            Feature saveFeature = iterable.next();
            saveFeature.setOperateId(operateId);
            saveList.add(saveFeature);
            if(saveList.size()>2000||!iterable.hasNext()){
                featureMapper.saveList(saveList);
                for (Feature feature : saveList) {
                    idList.add(feature.getId());
                }
                saveList.clear();
            }
        }
        return idList;
    }
    private void dealFeature(Feature feature) {
        if (feature.getStrand() == null) {
            feature.setStrand(1);
        }
        if (feature.getName() == null) {
            feature.setName(GenbankAnnotationKey.DEFAULT_FEATURE_NAME);
        }
        if (feature.getIsTemporary() == null) {
            feature.setIsTemporary(true);
        }
        if(feature.getSort()==null){
            if (Boolean.TRUE.equals(feature.getIsTemporary())) {
                feature.setSort(1);
            } else {
                feature.setSort(10);
            }
        }
        if (feature.getLocations() == null) {
            List<Location> list = new ArrayList<>();
            Location location = new Location(feature.getStart(), feature.getEnd());
            list.add(location);
            feature.setLocations(list);
        }
    }

    public AjaxResult updateFeature(Feature feature) {
        if (feature.getId() == null) {
            return AjaxResult.error("Saving feature failed");
        }
        featureMapper.update(feature);
        return AjaxResult.success();
    }

     
    public int removeFeatureList(Long sequenceId, List<Long> idList) {
        int count = 0;
        List<Long> removeList = new ArrayList<>(2000);
                 Iterator<Long> iterable = idList.iterator();
        while (iterable.hasNext()){
            removeList.add(iterable.next());
            if(removeList.size()>2000||!iterable.hasNext()){
                int c = featureMapper.removeByIdList(sequenceId, removeList);
                count+=c;
                removeList.clear();
            }
        }
        return count;
    }

     
    public void removeBySequenceIdList(List<Long> idList, Boolean isTemporary) {
                 List<Long> list = new ArrayList<>(2000);
        Iterator<Long> iterable = idList.iterator();
        while (iterable.hasNext()){
            list.add(iterable.next());
            if(list.size()>2000||!iterable.hasNext()){
                featureMapper.removeBySequenceIdList(list, isTemporary);
                list.clear();
            }
        }
    }
    public List<Feature> listFeatureFromRedis(Long sequenceId){
        List<Feature> list = redisService.getCacheList(CacheConstants.PREFIX_FEATURE_LIST+sequenceId);
        for (Feature feature : list) {
            Map<String,List<String>> m = feature.getNotes();
            if(m!=null){
                m.remove("@type");
            }
        }
        return list;
    }

     
    private Location adjustRangeToInsert(Range rangeToBeAdjusted,int insertStart,int insertLength){
        Location newRange = new Location(rangeToBeAdjusted.getStart().intValue(),rangeToBeAdjusted.getEnd().intValue());
        if(rangeToBeAdjusted.getStart()>rangeToBeAdjusted.getEnd()){
            if(rangeToBeAdjusted.getEnd()>=insertStart){
                newRange.setStart(newRange.getStart()+insertLength);
                newRange.setEnd(newRange.getEnd()+insertLength);
            }else if(rangeToBeAdjusted.getStart()>=insertStart){
                newRange.setStart(newRange.getStart()+insertLength);
            }
        }else{
            if(rangeToBeAdjusted.getStart()>=insertStart){
                newRange.setStart(newRange.getStart()+insertLength);
                newRange.setEnd(newRange.getEnd()+insertLength);
            }else if(rangeToBeAdjusted.getEnd()>=insertStart){
                newRange.setEnd(newRange.getEnd()+insertLength);
            }
        }
        return newRange;
    }
    private List<Feature> adjustAnnotationsToDelete(List<Feature> annotationsToBeAdjusted,Range range,int maxLength,boolean isEditFeatureName){
        List<Feature> result = new ArrayList<>();
        for (Feature feature : annotationsToBeAdjusted) {
            Range annotation = new Range(feature.getStart(),feature.getEnd());
            Range newRange = adjustRangeToDeletionOfAnotherRange(annotation, range, maxLength);
            if(newRange==null){
                continue;
            }
            if(isEditFeatureName && (feature.getEnd().intValue()-feature.getStart().intValue()!=
                    newRange.getEnd().intValue()-newRange.getStart().intValue())){
                feature.nameAppend("cut");
            }
            feature.setStart(newRange.getStart()).setEnd(newRange.getEnd());
            result.add(feature);
            if(feature.getLocations()!=null && !feature.getLocations().isEmpty()){
                List<Location> locations = new ArrayList<>();
                for (Location location : feature.getLocations()) {
                    Range nr = adjustRangeToDeletionOfAnotherRange(new Range(location), range, maxLength);
                    if(nr!=null){
                        location.setStart(nr.getStart()).setEnd(nr.getEnd());
                        locations.add(location);
                    }
                }
                feature.setLocations(locations);
            }
        }
        return result;
    }
    private Range adjustRangeToDeletionOfAnotherRange(Range rangeToBeAdjusted, Range anotherRange, int maxLength){
        Range trimmedRange = trimRangeByAnotherRange(rangeToBeAdjusted, anotherRange, maxLength);
        if(trimmedRange!=null){
            List<Range> nonCircularDeletionRanges = splitRangeIntoTwoPartsIfItIsCircular(anotherRange, maxLength);
            for (Range nonCircularDeletionRange : nonCircularDeletionRanges) {
                int deletionLength = nonCircularDeletionRange.getEnd() - nonCircularDeletionRange.getStart() + 1;
                if (trimmedRange.getStart() > trimmedRange.getEnd()) {
                    if (nonCircularDeletionRange.getStart() < trimmedRange.getEnd()) {
                        trimmedRange.setStart(trimmedRange.getStart()-deletionLength);
                        trimmedRange.setEnd(trimmedRange.getEnd()-deletionLength);
                    } else if (nonCircularDeletionRange.getStart() < trimmedRange.getStart()) {
                        trimmedRange.setStart(trimmedRange.getStart()-deletionLength);
                    }
                } else {
                    if (nonCircularDeletionRange.getStart() < trimmedRange.getStart()) {
                        trimmedRange.setStart(trimmedRange.getStart()-deletionLength);
                        trimmedRange.setEnd(trimmedRange.getEnd()-deletionLength);
                    } else if (nonCircularDeletionRange.getStart() < trimmedRange.getEnd()) {
                        trimmedRange.setEnd(trimmedRange.getEnd()-deletionLength);
                    }
                }
            }
        }
        return trimmedRange;
    }
    private Range trimRangeByAnotherRange(Range rangeToBeTrimmed, Range trimmingRange, int sequenceLength){
        if(rangeToBeTrimmed==null||trimmingRange==null||rangeToBeTrimmed.getStart()==null||
                rangeToBeTrimmed.getEnd()==null||trimmingRange.getStart()==null||trimmingRange.getEnd()==null||
                rangeToBeTrimmed.getStart()<0||rangeToBeTrimmed.getEnd()<0||trimmingRange.getStart()<0||
                trimmingRange.getEnd()<0){
            return null;
        }
                 List<Range> overlaps = getOverlapsOfPotentiallyCircularRanges(rangeToBeTrimmed,trimmingRange,sequenceLength);
        if(overlaps.isEmpty()){
            return rangeToBeTrimmed;
        }
        List<Range> splitRangesToBeTrimmed = splitRangeIntoTwoPartsIfItIsCircular(rangeToBeTrimmed, sequenceLength);
        for (int index = 0; index < splitRangesToBeTrimmed.size(); index++) {
            Range nonCircularRangeToBeTrimmed = splitRangesToBeTrimmed.get(index);
            for (Range overlap : overlaps) {
                if(nonCircularRangeToBeTrimmed!=null){
                    nonCircularRangeToBeTrimmed = trimNonCicularRangeByAnotherNonCircularRange(nonCircularRangeToBeTrimmed, overlap);
                }
            }
            splitRangesToBeTrimmed.set(index,nonCircularRangeToBeTrimmed);
        }
        List<Range> outputSplitRanges = new ArrayList<>();
        for (Range trimmedRange : splitRangesToBeTrimmed) {
            if(trimmedRange!=null){
                outputSplitRanges.add(trimmedRange);
            }
        }
        Range outputTrimmedRange = null;
        if (outputSplitRanges.size() == 1) {
            outputTrimmedRange = outputSplitRanges.get(0);
        } else if (outputSplitRanges.size() == 2) {
            if (outputSplitRanges.get(0).getStart() < outputSplitRanges.get(1).getStart()) {
                outputTrimmedRange = new Range(outputSplitRanges.get(1).getStart(),outputSplitRanges.get(0).getEnd());
            } else {
                outputTrimmedRange = new Range(outputSplitRanges.get(0).getStart(),outputSplitRanges.get(1).getEnd());
            }
        }
        return outputTrimmedRange;
    }
    private List<Range> getOverlapsOfPotentiallyCircularRanges(Range rangeA, Range rangeB,int maxRangeLength){
        List<Range> normalizedRangeA = splitRangeIntoTwoPartsIfItIsCircular(rangeA,maxRangeLength);
        List<Range> normalizedRangeB = splitRangeIntoTwoPartsIfItIsCircular(rangeB,maxRangeLength);
        List<Range> overlaps = new ArrayList<>();
        for (Range nonCircularRangeA : normalizedRangeA) {
            for (Range nonCircularRangeB : normalizedRangeB) {
                Range overlap = getOverlapOfNonCircularRanges(nonCircularRangeA, nonCircularRangeB);
                if (overlap!=null) {
                    overlaps.add(overlap);
                }
            }
        }
        return overlaps;
    }

    private List<Range> splitRangeIntoTwoPartsIfItIsCircular(Range range, int sequenceLength){
        List<Range> ranges = new ArrayList<>();
        if(range.getStart()>range.getEnd()){
                         ranges.add(new Range(0,range.getEnd(),"end"));
            ranges.add(new Range(range.getStart(),sequenceLength-1,"beginning"));
        }else{
            ranges.add(new Range(range.getStart(),range.getEnd(),"beginningAndEnd"));
        }
        return ranges;
    }
    private Range trimNonCicularRangeByAnotherNonCircularRange(Range rangeToBeTrimmed,Range trimmingRange){
        Range outputTrimmedRange = null;
        if(rangeToBeTrimmed==null){
            return null;
        }
        if (rangeToBeTrimmed.getStart() < trimmingRange.getStart()) {
            if (rangeToBeTrimmed.getEnd() < trimmingRange.getStart()) {
                outputTrimmedRange = new Range(rangeToBeTrimmed.getStart(),rangeToBeTrimmed.getEnd());
            } else {
                if (rangeToBeTrimmed.getEnd() > trimmingRange.getEnd()) {
                    outputTrimmedRange = new Range(rangeToBeTrimmed.getStart(),rangeToBeTrimmed.getEnd());
                } else {
                    outputTrimmedRange = new Range(rangeToBeTrimmed.getStart(),trimmingRange.getStart() - 1);
                }
            }
        } else {
            if (rangeToBeTrimmed.getEnd() <= trimmingRange.getEnd()) {
            } else {
                if (rangeToBeTrimmed.getStart() > trimmingRange.getEnd()) {
                    outputTrimmedRange = new Range(rangeToBeTrimmed.getStart(),rangeToBeTrimmed.getEnd());
                } else {
                    outputTrimmedRange = new Range(trimmingRange.getEnd() + 1,rangeToBeTrimmed.getEnd());
                }
            }
        }
        return outputTrimmedRange;
    }
    private Range getOverlapOfNonCircularRanges(Range rangeA,Range rangeB){
        if (rangeA.getStart() < rangeB.getStart()) {
            if (rangeA.getEnd() < rangeB.getStart()) {
                return null;
            } else {
                if (rangeA.getEnd() < rangeB.getEnd()) {
                    return new Range(rangeB.getStart(),rangeA.getEnd());
                } else {
                    return new Range(rangeB.getStart(),rangeB.getEnd());
                }
            }
        } else {
            if (rangeA.getStart() > rangeB.getEnd()) {
                return null;
            } else {
                if (rangeA.getEnd() < rangeB.getEnd()) {
                    return new Range(rangeA.getStart(),rangeA.getEnd());
                } else {
                    return new Range(rangeA.getStart(),rangeB.getEnd());
                }
            }
        }
    }
    public List<Feature> adjustAnnotationsToInsert(List<Feature> list,int insertStart,int insertLength,boolean isEditFeatureName){
        List<Feature> result = new ArrayList<>();
        for (Feature oldFeature : list) {
            Feature updateFeature = new Feature(oldFeature);
            Range rangeToBeAdjusted = new Range(oldFeature.getStart(),oldFeature.getEnd());
            Location newRange = adjustRangeToInsert(rangeToBeAdjusted,insertStart,insertLength);
            if(isEditFeatureName &&
                    (oldFeature.getEnd().intValue()-oldFeature.getStart().intValue()!=newRange.getEnd().intValue()-newRange.getStart().intValue())){
                updateFeature.nameAppend("insert");
            }
            updateFeature.setStart(newRange.getStart()).setEnd(newRange.getEnd());
            if(oldFeature.getLocations()!=null && !oldFeature.getLocations().isEmpty()){
                List<Location> updateLocations = new ArrayList<>();
                updateFeature.setLocations(updateLocations);
                for (Location l : oldFeature.getLocations()) {
                    rangeToBeAdjusted.setStart(l.getStart()).setEnd(l.getEnd());
                    Location nl = adjustRangeToInsert(rangeToBeAdjusted,insertStart,insertLength);
                    updateLocations.add(nl);
                }
            }
            result.add(updateFeature);
        }
        return result;
    }

     
    public List<Feature> changeFeatureIndex(Feature param, OperateEnum operateEnum, SequenceOperate sequenceOperate,List<Feature> list,
                                   boolean isChangeDatabase,boolean isEditFeatureName) {
        Long operateId = sequenceOperate.getId();
        if(list==null||list.isEmpty()){
            return null;
        }
        List<Feature> result;
        if(operateEnum == OperateEnum.CUT){
            result = adjustAnnotationsToDelete(list,new Range(param.getStart(),param.getEnd()), param.getOldSequenceLength(),isEditFeatureName);
        }else{
            if(operateEnum == OperateEnum.REPLACE){
                list = adjustAnnotationsToDelete(list,new Range(param.getStart(),param.getEnd()), param.getOldSequenceLength(),isEditFeatureName);
            }
            result = adjustAnnotationsToInsert(list,param.getStart(),param.getSequenceLength(),isEditFeatureName);
            if(sequenceOperate.getFeatures()!=null&&!sequenceOperate.getFeatures().isEmpty()){
                result.addAll(adjustAnnotationsToInsert(sequenceOperate.getFeatures(),0,param.getStart(),isEditFeatureName));
            }
        }
        for (Feature feature : result) {
            feature.setSequenceId(param.getSequenceId());
        }
        if(isChangeDatabase){
                         featureMapper.updateOperateIdDel(param.getSequenceId(),operateId);
            this.saveFeatureListOperate(result,operateId);
        }
        return result;
    }
}
