package com.hmzhkj.gene.service;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.FileUploadUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.constant.ImportConfigEnum;
import com.hmzhkj.gene.constant.OperateEnum;
import com.hmzhkj.gene.constant.PyFeatureEnum;
import com.hmzhkj.gene.domain.Feature;
import com.hmzhkj.gene.domain.Programme;
import com.hmzhkj.gene.domain.Sequence;
import com.hmzhkj.gene.domain.SequenceOperate;
import com.hmzhkj.gene.mapper.FeatureMapper;
import com.hmzhkj.gene.mapper.ProgrammeMapper;
import com.hmzhkj.gene.mapper.SequenceMapper;
import com.hmzhkj.gene.mapper.SequenceOperateMapper;
import com.hmzhkj.gene.model.*;
import com.hmzhkj.gene.util.GffToFeature;
import com.hmzhkj.gene.util.JsonToGenbank;
import com.hmzhkj.gene.util.SeqUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SequenceOperateService {
    private final SequenceOperateMapper sequenceOperateMapper;
    private final SequenceMapper sequenceMapper;
    private final ProgrammeMapper programmeMapper;
    private final FeatureService featureService;
    private final FeatureMapper featureMapper;
    private final CommonService commonService;
    private final PyService pyService;
    private final String[] gffArray = {"gtf","gff","gff3"};
    private final String[] gbArray = {"gbff","gb","gbk"};
    private final String[] strandArrayA = {"1","+","+1"};
    private final String[] strandArrayB = {"-","-1"};
         private final String[] fileRollBackType = {OperateEnum.FEATURE_CUT_BY_TYPE.getValue(),OperateEnum.IMPORT_CONFIG.getValue(),
            OperateEnum.FEATURE_INSERT_GLOBAL.getValue(),OperateEnum.FEATURE_CUT_REDUCTION.getValue(),OperateEnum.CODON_REPLACE.getValue()};
    @Value("${path.sequence}")
    private String sequencePath;
    public List<SequenceOperate> listSequenceOperate(SequenceOperate sequenceOperate){
        Sequence sequence = sequenceMapper.get(new Sequence().setId(sequenceOperate.getSequenceId()));
        String folderPath = commonService.getSequenceFolderPath(sequence.getProgrammeId());
                 List<SequenceOperate> list =  sequenceOperateMapper.list(sequenceOperate);
        for (SequenceOperate operate : list) {
            if(operate.getEnd()!=null){
                operate.setEnd(operate.getEnd()-1);
            }
            StringBuilder sb = new StringBuilder();
            File f = new File(folderPath+operate.getId().toString()+".xlsx");
            if(f.exists()){
                sb.append("xlsx");
            }
            f = new File(folderPath+operate.getId().toString()+".txt");
            if(f.exists()){
                sb.append("txt");
            }
            operate.setErrExcelPath(sb.toString());
        }
        return list;
    }

     
         public AjaxResult parseFromTxt(String txtName,Sequence sequence,boolean isEditFeatureName){
        List<Feature> features = featureMapper.list(new Feature().setSequenceId(sequence.getId()));
        String sequenceFolder = commonService.getSequenceFolderPath(sequence.getProgrammeId());
        FileReader fr = new FileReader(sequenceFolder+txtName);
        List<String> lineList = fr.readLines();
        FileReader fileReader = new FileReader(sequenceFolder+sequence.getFileName());
        String seq = fileReader.readString();
                          if(lineList.size()<2){
            throw new ServiceException("txt is empty");
        }
        String[] strings = lineList.get(0).split("\t");
        if(strings.length<5){
            throw new ServiceException("txt missing parameter");
        }
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].toUpperCase();
        }
                 String regex = "^\\d+$";
        int chrIndex = ArrayUtils.indexOf(strings, ImportConfigEnum.CHR.getValue());
        int startIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.START.getValue());
        int endIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.END.getValue());
        int editTypeIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.EDIT_TYPE.getValue());
        int seqIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.SEQ.getValue());
        if(chrIndex==-1||startIndex==-1||endIndex==-1||editTypeIndex==-1||seqIndex==-1){
            return AjaxResult.error("txt missing parameter");
        }
        int featureNameIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.FEATURE_NAME.getValue());
        int featureTypeIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.FEATURE_TYPE.getValue());
        int strandIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.STRAND.getValue());
        int oriSeqIndex = ArrayUtils.indexOf(strings,ImportConfigEnum.ORI_SEQ.getValue());
        List<ImportConfigParam> list = new ArrayList<>();
        for (int i = 1; i < lineList.size(); i++) {
            String[] array = lineList.get(i).split("\t");
            if(array.length==0||array.length<5){
                continue;
            }
            if(array.length<strings.length){
                array=Arrays.copyOf(array,strings.length);
            }
            if(!array[endIndex].matches(regex)||!array[startIndex].matches(regex)){
                return AjaxResult.error("Number type conversion failed");
            }
                         if(!array[chrIndex].trim().equals(sequence.getName())){
                continue;
            }
            ImportConfigParam importConfigParam = new ImportConfigParam();
            importConfigParam.setStrand(1);
            if(strandIndex!=-1 && StringUtils.isNotEmpty(array[strandIndex])){
                if(!ArrayUtils.contains(strandArrayA,array[strandIndex])&&
                        !ArrayUtils.contains(strandArrayB,array[strandIndex])){
                    throw new ServiceException("Strand conversion failed");
                }
                if(ArrayUtils.contains(strandArrayB,array[strandIndex])){
                    importConfigParam.setStrand(-1);
                }
            }
                         importConfigParam.setChr(array[chrIndex].trim()).setSeq(array[seqIndex].trim()).setEditType(array[editTypeIndex].trim().toUpperCase())
                    .setStart(Integer.valueOf(array[startIndex]).intValue()-1).setEnd(Integer.valueOf(array[endIndex]));
            importConfigParam.setFeatureStart(importConfigParam.getStart()).setFeatureEnd(importConfigParam.getEnd());
            if(featureNameIndex!=-1&&StringUtils.isNotEmpty(array[featureNameIndex])){
                importConfigParam.setFeatureName(array[featureNameIndex].trim());
            }
            if(featureTypeIndex!=-1 && StringUtils.isNotEmpty(array[featureTypeIndex])){
                importConfigParam.setFeatureType(array[featureTypeIndex].trim());
            }
            if(oriSeqIndex!=-1 && StringUtils.isNotEmpty(array[oriSeqIndex])){
                importConfigParam.setOriSeq(array[oriSeqIndex].trim());
            }
            if(!OperateEnum.CUT.getValue().equals(importConfigParam.getEditType())&&
                    !OperateEnum.INSERT.getValue().equals(importConfigParam.getEditType())&&
                    !OperateEnum.REPLACE.getValue().equals(importConfigParam.getEditType())){
                throw new ServiceException("Edit_type error");
            }
            if(importConfigParam.getStart().intValue()>sequence.getBpLength()-1){
                throw new ServiceException("Start error");
            }
            if((OperateEnum.CUT.getValue().equals(importConfigParam.getEditType()) ||OperateEnum.REPLACE.getValue().equals(importConfigParam.getEditType()))
            &&importConfigParam.getEnd().intValue()>sequence.getBpLength()){
                throw new ServiceException("End error");
            }
                         if(importConfigParam.getStrand().equals(-1)){
                if(StringUtils.isNotEmpty(importConfigParam.getSeq())){
                    importConfigParam.setSeq(SeqUtil.reverseComplement(importConfigParam.getSeq()));
                }
                if(StringUtils.isNotEmpty(importConfigParam.getOriSeq())){
                    importConfigParam.setOriSeq(SeqUtil.reverseComplement(importConfigParam.getOriSeq()));
                }
            }
                         if((OperateEnum.CUT.getValue().equals(importConfigParam.getEditType()) ||OperateEnum.REPLACE.getValue().equals(importConfigParam.getEditType()))&&
                    StringUtils.isNotEmpty(importConfigParam.getOriSeq())){
                String right = seq.substring(importConfigParam.getStart(),importConfigParam.getEnd());
                if(!right.equals(importConfigParam.getOriSeq())){
                    throw new ServiceException("Ori_seq error in line:"+(i+1));
                }
            }
                         if((OperateEnum.CUT.getValue().equals(importConfigParam.getEditType()) ||OperateEnum.REPLACE.getValue().equals(importConfigParam.getEditType()))
                &&importConfigParam.getStart().intValue()>importConfigParam.getEnd().intValue()){
                                 ImportConfigParam record = new ImportConfigParam();
                record.setChr(importConfigParam.getChr()).setEnd(seq.length())
                        .setEditType(importConfigParam.getEditType()).setSeq(importConfigParam.getSeq())
                        .setFeatureStart(importConfigParam.getStart().intValue()).setFeatureEnd(importConfigParam.getEnd().intValue());
                list.add(record);
                                 importConfigParam.setStart(0).setEditType(OperateEnum.CUT.getValue()).setSeq(null);
            }
            list.add(importConfigParam);
        }
        if(list.isEmpty()){
            return AjaxResult.success();
        }
                 list.sort(Comparator.comparing(ImportConfigParam::getStart));
        for (int i = 1; i < list.size(); i++) {
            ImportConfigParam last = list.get(i-1);
            ImportConfigParam current = list.get(i);
                         if(OperateEnum.CUT.getValue().equals(last.getEditType())||OperateEnum.REPLACE.getValue().equals(last.getEditType())){
                                 if((OperateEnum.CUT.getValue().equals(current.getEditType())||OperateEnum.REPLACE.getValue().equals(current.getEditType()))
                        &&current.getStart().intValue()< last.getEnd().intValue()){
                    throw new ServiceException("Interval overlap");
                }
                                 if(OperateEnum.INSERT.getValue().equals(current.getEditType())&&current.getStart().intValue()< last.getEnd().intValue()-1){
                    throw new ServiceException("Interval overlap");
                }
            }
        }
        String sourceName = IdUtil.fastSimpleUUID()+".gbff";
        SequenceOperate sequenceOperate = new SequenceOperate();
        sequenceOperate.setCanRollback(true).setSourcePath(sourceName);
        sequenceOperate.setSequenceId(sequence.getId()).setType(OperateEnum.IMPORT_CONFIG.getValue());
        this.save(sequenceOperate);
        StringBuilder sb = new StringBuilder(seq);
                 Collections.reverse(list);
                 for (ImportConfigParam importConfigParam : list) {
            int start = importConfigParam.getStart();
            int end = importConfigParam.getEnd();
            Feature param = new Feature();
            param.setSequenceId(sequence.getId());
            param.setStart(start).setEnd(end-1);
            param.setOldSequenceLength(sb.length());
            OperateEnum operateEnum;
            Feature feature = null;
                         if(OperateEnum.CUT.getValue().equalsIgnoreCase(importConfigParam.getEditType())){
                sb.delete(start,end);
                operateEnum = OperateEnum.CUT;
            }else if(OperateEnum.INSERT.getValue().equalsIgnoreCase(importConfigParam.getEditType())){
                                 sb.insert(start,importConfigParam.getSeq());
                param.setSequenceLength(importConfigParam.getSeq().length());
                operateEnum = OperateEnum.INSERT;
                if(StringUtils.isNotEmpty(importConfigParam.getFeatureType())){
                    feature = new Feature(importConfigParam);
                }
            }else{
                sb.replace(start, end, importConfigParam.getSeq());
                param.setSequenceLength(importConfigParam.getSeq().length());
                operateEnum = OperateEnum.REPLACE;
                if(StringUtils.isNotEmpty(importConfigParam.getFeatureType())){
                    feature = new Feature(importConfigParam);
                }
            }
            features = featureService.changeFeatureIndex(param,operateEnum,sequenceOperate,features,false,isEditFeatureName);
                         if(feature!=null){
                features.add(feature.setSequenceId(sequence.getId()));
            }
        }
        String str = sb.toString();
        sequenceMapper.update(new Sequence().setId(sequence.getId()).setBpLength(str.length()));
        Programme up =new Programme().setId(sequence.getProgrammeId()).setUpdateTime(new Date());
        up.setUpdateStaffNo(SecurityUtils.getUserId());
        programmeMapper.updateById(up);
                 featureMapper.updateOperateIdDel(sequence.getId(),sequenceOperate.getId());
        featureService.saveFeatureListOperate(features,sequenceOperate.getId());
                 FileWriter fwA = new FileWriter(sequenceFolder+sequence.getFileName());
        fwA.write(str);
        FileWriter fwB = new FileWriter(sequenceFolder+sourceName);
        fwB.write(seq);
        return AjaxResult.success("Operation successful",list);
    }
    public AjaxResult save(SequenceOperate sequenceOperate){
        String userId = SecurityUtils.getUserId();
        String nickName = SecurityUtils.getLoginUser().getSysUser().getNickName();
        sequenceOperate.setCreateBy(userId).setCreateByName(nickName);
        Integer maxOrder = sequenceOperateMapper.countMaxOrder(sequenceOperate.getSequenceId());
        if(maxOrder==null){
            maxOrder = 0;
        }
        sequenceOperate.setOrder(maxOrder+1);
        sequenceOperateMapper.save(sequenceOperate);
        return AjaxResult.success("Operation successful");
    }
    public AjaxResult removeList(List<SequenceOperate> list,Long sequenceId){
        List<Long> idList = new ArrayList<>();
        for (SequenceOperate sequenceOperate : list) {
            idList.add(sequenceOperate.getId());
            if(idList.size()>2000){
                sequenceOperateMapper.removeByIdList(sequenceId,idList);
                idList.clear();
            }
        }
        if(!idList.isEmpty()){
            sequenceOperateMapper.removeByIdList(sequenceId,idList);
            idList.clear();
        }
        return AjaxResult.success("Operation successful");
    }
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult removeRepeatFeature(FeatureRepeatCondition featureRepeatCondition){
        List<Feature> featureList= featureMapper.listRepeat(featureRepeatCondition);
        if(featureList!=null && !featureList.isEmpty()){
            SequenceOperate sequenceOperate = new SequenceOperate();
            sequenceOperate.setCanRollback(true).setType(OperateEnum.FEATURE_CUT_LIST.getValue());
            sequenceOperate.setSequenceId(featureRepeatCondition.getSequenceId());
            this.save(sequenceOperate);
            for (Feature feature : featureList) {
                feature.setSequenceId(featureRepeatCondition.getSequenceId());
                                 featureMapper.removeRepeat(feature.setCutOperateId(sequenceOperate.getId()));
            }
        }
        return AjaxResult.success();
    }
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult pyOperateFeature(PyFeatureParam pyFeatureParam, PyFeatureEnum pyFeatureEnum) throws IOException {
        if(pyFeatureEnum==PyFeatureEnum.DELETE_FEATURE && (pyFeatureParam.getTypeList()==null || pyFeatureParam.getTypeList().isEmpty()||pyFeatureParam.getSequenceId()==null)){
            throw new ServiceException("Incomplete parameters");
        }
        if(pyFeatureEnum==PyFeatureEnum.REDUCE_GENOME && (pyFeatureParam.getTypeRemainMap()==null || pyFeatureParam.getTypeRemainMap().isEmpty()||pyFeatureParam.getSequenceId()==null)){
            throw new ServiceException("Incomplete parameters");
        }
        if(pyFeatureEnum==PyFeatureEnum.GLOBAL_INSERTION &&
                (StringUtils.isEmpty(pyFeatureParam.getSequence())||StringUtils.isEmpty(pyFeatureParam.getFeatureName())
                ||StringUtils.isEmpty(pyFeatureParam.getFeatureType())||pyFeatureParam.getSequenceId()==null||pyFeatureParam.getFeatureMap()==null||
        pyFeatureParam.getFeatureMap().isEmpty())){
            pyFeatureParam.sequenceToUpperCase();
            if(Integer.valueOf(-1).equals(pyFeatureParam.getStrand())&& com.hmzhkj.common.core.utils.StringUtils.isNotEmpty(pyFeatureParam.getSequence())){
                pyFeatureParam.reverseComplementSequence();
            }
            throw new ServiceException("Incomplete parameters");
        }
        Sequence sequence = sequenceMapper.get(new Sequence().setId(pyFeatureParam.getSequenceId()));
        sequence.setFeatures(featureMapper.list(new Feature().setSequenceId(pyFeatureParam.getSequenceId())));
        String sequenceFolder = commonService.getSequenceFolderPath(sequence.getProgrammeId());
        String sourcePath = sequenceFolder+sequence.getFileName();
        FileReader fileReader = new FileReader(sourcePath);
        String s = fileReader.readString();
        sequence.setSequence(s);
        List<String> lineGenBankList = JsonToGenbank.convert(sequence);
        File gbffFile = new File(sequenceFolder+IdUtil.fastSimpleUUID() +".gbff");
        FileWriter gbffFileWriter = new FileWriter(gbffFile);
        gbffFileWriter.appendLines(lineGenBankList);
        String inputPath = gbffFile.getPath();
                 SequenceOperate sequenceOperate = new SequenceOperate();
        sequenceOperate.setCanRollback(true).setSourcePath(gbffFile.getName());
        sequenceOperate.setSequenceId(pyFeatureParam.getSequenceId());
        if(pyFeatureEnum==PyFeatureEnum.DELETE_FEATURE){
            sequenceOperate.setType(OperateEnum.FEATURE_CUT_BY_TYPE.getValue());
        }else if(pyFeatureEnum==PyFeatureEnum.REDUCE_GENOME){
            sequenceOperate.setType(OperateEnum.FEATURE_CUT_REDUCTION.getValue());
        }else if(pyFeatureEnum==PyFeatureEnum.CODON_REPLACE){
            sequenceOperate.setType(OperateEnum.CODON_REPLACE.getValue());
        }else if(pyFeatureEnum==PyFeatureEnum.MERGE_FEATURE){
            sequenceOperate.setType(OperateEnum.FEATURE_MERGE.getValue());
        }else{
            sequenceOperate.setType(OperateEnum.FEATURE_INSERT_GLOBAL.getValue());
        }
        this.save(sequenceOperate);
                 pyFeatureParam.setPath(inputPath).setExcelPath(sequenceFolder+sequenceOperate.getId()+".xlsx");
        pyFeatureParam.setOperateId(sequenceOperate.getId()).setSequenceName(sequence.getName());
        PyFeatureReturnParam result = pyService.operateFeature(pyFeatureParam,pyFeatureEnum);
        List<Feature> features = result.getFeatures();
        FileReader fileReaderA = new FileReader(inputPath);
        String a = fileReaderA.readString();
        for (Feature f : features) {
            f.setSequenceId(pyFeatureParam.getSequenceId());
            f.init(a.length());
        }
        featureMapper.updateOperateIdDel(pyFeatureParam.getSequenceId(),sequenceOperate.getId());
        featureService.saveFeatureListOperate(features,sequenceOperate.getId());
                 if(pyFeatureEnum!=PyFeatureEnum.MERGE_FEATURE){
            FileReader fileReaderB = new FileReader(sourcePath);
            String b = fileReaderB.readString();
            FileWriter fw = new FileWriter(sourcePath);
            fw.write(a);
            fw = new FileWriter(inputPath);
            fw.write(b);
            sequenceMapper.update(new Sequence().setId(pyFeatureParam.getSequenceId()).setBpLength(a.length()));
        }
        return AjaxResult.success();
    }
     
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult rollback(Long sequenceId,Long id){
        List<SequenceOperate> sourceList = sequenceOperateMapper.listRollback(new SequenceOperate().setSequenceId(sequenceId).setId(id));
        List<SequenceOperate> targetList = new ArrayList<>(sourceList.size());
        for (SequenceOperate obj : sourceList) {
            if(Boolean.FALSE.equals(obj.getCanRollback())){
                return AjaxResult.error("Not operable");
            }

            if(OperateEnum.INSERT.getValue().equalsIgnoreCase(obj.getType())||
                    OperateEnum.CUT.getValue().equalsIgnoreCase(obj.getType())||
                    OperateEnum.REPLACE.getValue().equalsIgnoreCase(obj.getType())||
                    ArrayUtils.indexOf(fileRollBackType,obj.getType().toUpperCase())!=-1){
                SequenceOperate delObj = new SequenceOperate();
                delObj.setStart(obj.getStart());
                if(OperateEnum.CUT.getValue().equalsIgnoreCase(obj.getType())){
                    delObj.setType(OperateEnum.INSERT.getValue());
                    delObj.setEnd(delObj.getStart());
                    delObj.setSequence(obj.getPreSequence());
                }else if(OperateEnum.INSERT.getValue().equalsIgnoreCase(obj.getType())){
                    delObj.setType(OperateEnum.CUT.getValue());
                    delObj.setEnd(delObj.getStart()+obj.getSequence().length());
                }else if(OperateEnum.REPLACE.getValue().equalsIgnoreCase(obj.getType())){
                    delObj.setType(OperateEnum.REPLACE.getValue());
                    delObj.setSequence(obj.getPreSequence());
                    delObj.setEnd(delObj.getStart()+obj.getSequence().length());
                }else if(ArrayUtils.indexOf(fileRollBackType,obj.getType().toUpperCase())!=-1){
                    delObj.setStart(1).setEnd(1);
                    delObj.setSourcePath(obj.getSourcePath());
                    delObj.setType(obj.getType().toUpperCase());
                }
                targetList.add(delObj);
            }
        }
        SequenceOperate sequenceOperate = sourceList.get(sourceList.size()-1);
        featureService.rollBack(sequenceId,sequenceOperate.getId());
        removeList(sourceList,sequenceId);
        if(!targetList.isEmpty()){
            operateSequenceFile(targetList,sequenceId,true);
        }
        return AjaxResult.success("Not operable");
    }

     
                      @Transactional(rollbackFor = Exception.class)
    public AjaxResult operateFeature(Feature feature,OperateEnum operateEnum){
        SequenceOperate sequenceOperate = new SequenceOperate();
        sequenceOperate.setCanRollback(true);
        sequenceOperate.setSequenceId(feature.getSequenceId()).setType(operateEnum.getValue());
        this.save(sequenceOperate);
        feature.setOperateId(sequenceOperate.getId());
        if(OperateEnum.FEATURE_INSERT==operateEnum){
            featureService.saveFeature(feature);
            return AjaxResult.success(feature.getId());
        }else if(OperateEnum.FEATURE_UPDATE==operateEnum){
            feature.setCutOperateId(sequenceOperate.getId());
            featureMapper.updateDelById(feature);
            featureService.saveFeature(feature.setCutOperateId(null));
            return AjaxResult.success(feature.getId());
        }else if(OperateEnum.FEATURE_CUT==operateEnum||OperateEnum.FEATURE_CUT_LIST==operateEnum){
            feature.setCutOperateId(sequenceOperate.getId());
            featureMapper.updateDelById(feature);
        }
       return AjaxResult.success();
    }
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importFeature(String[] sourceFileNames,Long sequenceId) throws IOException {
        SequenceOperate sequenceOperate = new SequenceOperate();
        sequenceOperate.setCanRollback(true);
        sequenceOperate.setSequenceId(sequenceId).setType(OperateEnum.FEATURE_INSERT_LIST.getValue());
        this.save(sequenceOperate);
        Sequence sequence = sequenceMapper.get(new Sequence().setId(sequenceId));
        Programme programme = programmeMapper.selectById(sequence.getProgrammeId());
        String sequenceFolder = commonService.getSequenceFolderPath(programme.getId());
        List<String> fileNameList = new ArrayList<>(sourceFileNames.length);
        for (String s : sourceFileNames) {
            String fileName = s;
            if(fileName.endsWith(".gz")){
                fileName = FileUploadUtils.decompress(new File(sequenceFolder + fileName), sequenceFolder);
            }
            String type = fileName.substring(fileName.lastIndexOf(".")+1);
            if(!ArrayUtils.contains(gffArray,type)){
                return AjaxResult.error("Parsing failed!"+type+" does not meet the requirements");
            }else{
                fileNameList.add(fileName);
            }
        }
        List<Feature> featureList = new ArrayList<>();
        for (String fileName : fileNameList) {
            FileReader fileReader = new FileReader(sequenceFolder+fileName);
            String type = fileName.substring(fileName.lastIndexOf(".")+1);
            List<String> lineList = fileReader.readLines();
            List<Feature> features = GffToFeature.convert(lineList,type);
                         for (Feature feature : features) {
                if(sequence.getName().equals(feature.getSequenceName())){
                    featureList.add(feature);
                }
            }
        }
        saveFeatureList(featureList,sequenceId,sequenceOperate.getId());
        return AjaxResult.success();
    }
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importFeatureFromGb(String[] sourceFileNames,Long sequenceId) throws IOException {
        SequenceOperate sequenceOperate = new SequenceOperate();
        sequenceOperate.setCanRollback(true);
        sequenceOperate.setSequenceId(sequenceId).setType(OperateEnum.FEATURE_INSERT_LIST.getValue());
        this.save(sequenceOperate);
        Sequence sequence = sequenceMapper.get(new Sequence().setId(sequenceId));
        Programme programme = programmeMapper.selectById(sequence.getProgrammeId());
        String sequenceFolder = commonService.getSequenceFolderPath(programme.getId());
        List<String> fileNameList = new ArrayList<>(sourceFileNames.length);
        for (String s : sourceFileNames) {
            String fileName = s;
            if(fileName.endsWith(".gz")){
                fileName = FileUploadUtils.decompress(new File(sequenceFolder + fileName), sequenceFolder);
            }
            String type = fileName.substring(fileName.lastIndexOf(".")+1);
            if(!ArrayUtils.contains(gbArray,type)){
                return AjaxResult.error("Parsing failed!"+type+" does not meet the requirements");
            }else{
                fileNameList.add(fileName);
            }
        }
        sequence.setFeatures(featureMapper.list(new Feature().setSequenceId(sequenceId)));
        String sourcePath = sequenceFolder+sequence.getFileName();
        FileReader fileReader = new FileReader(sourcePath);
        String s = fileReader.readString();
        sequence.setSequence(s);
        List<String> lineGenBankList = JsonToGenbank.convert(sequence);
        File gbffFile = new File(sequenceFolder+IdUtil.fastSimpleUUID() +".gbff");
        FileWriter gbffFileWriter = new FileWriter(gbffFile);
        gbffFileWriter.appendLines(lineGenBankList);
        String inputPath = gbffFile.getPath();
        PyFeatureParam pyFeatureParam = new PyFeatureParam();
        pyFeatureParam.setPath(inputPath);
        List<String> pathList = new ArrayList<>();
        for (String fileName : fileNameList) {
            pathList.add(sequenceFolder+fileName);
        }
        pyFeatureParam.setPathList(pathList);
        List<Feature> features = pyService.operateFeature(pyFeatureParam,PyFeatureEnum.IMPORT_FEATURE).getFeatures();
              File file = new File(inputPath);
        if(file.exists()){
            file.delete();
        }
        if(features.isEmpty()){
            return AjaxResult.error("No matching features");
        }
        for (Feature f : features) {
            f.setSequenceId(pyFeatureParam.getSequenceId());
            f.init(s.length());
        }
        saveFeatureList(features,sequenceId,sequenceOperate.getId());
        return AjaxResult.success();
    }
    private void saveFeatureList(List<Feature> features,Long sequenceId,Long operateId){
        List<Feature> saveFeatures = new ArrayList<>(2000);
        Iterator<Feature> iterable = features.iterator();
        while (iterable.hasNext()){
            Feature feature = iterable.next();
                         if("primer_bind".equals(feature.getType())){
                continue;
            }
            Map<String,List<String>> notes = feature.getNotes();
            if((notes.containsKey("label") &&  notes.get("label").get(0).contains("Primer"))||
                    (notes.containsKey("pragma") &&  notes.get("pragma").get(0).contains("Part"))){
                continue;
            }
            feature.setSequenceId(sequenceId).setOperateId(operateId);
            saveFeatures.add(feature);
            if(saveFeatures.size()>2000||!iterable.hasNext()){
                featureMapper.saveList(saveFeatures);
                saveFeatures.clear();
            }
        }
    }
     
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult operateSequenceFile(List<SequenceOperate> list,Long sequenceId,boolean isRollBack){
        Sequence sequence = sequenceMapper.get(new Sequence().setId(sequenceId));
        String sequenceFolder = getSequenceFolderPath(sequence.getProgrammeId());
        FileReader fileReader = new FileReader(sequenceFolder+sequence.getFileName());
        StringBuilder sb = new StringBuilder(fileReader.readString());
        int oldSequenceLength = sb.length();
                 for (int i = 0; i < list.size(); i++) {
            SequenceOperate sequenceOperate = list.get(i);
            int start = sequenceOperate.getStart()-1;
            int end = sequenceOperate.getEnd()-1;
            if(start==end){
                end++;
            }
            Feature param = new Feature();
            param.setSequenceId(sequenceId);
            param.setStart(start).setEnd(end-1);
            param.setOldSequenceLength(oldSequenceLength);
            OperateEnum operateEnum = OperateEnum.CUT;
            if(sequenceOperate.getSequence()!=null){
                sequenceOperate.setSequence(sequenceOperate.getSequence().toUpperCase());
            }
                         if(OperateEnum.CUT.getValue().equalsIgnoreCase(sequenceOperate.getType())){
                if(Boolean.TRUE.equals(sequenceOperate.getNeedSetPreSequence())){
                    if(start>end){
                        String preS = sb.substring(0,end)+sb.substring(start,sb.length());
                        sequenceOperate.setPreSequence(preS);
                    }else{
                        sequenceOperate.setPreSequence(sb.substring(start, end));
                    }
                }
                if(start>end){
                                         sb.delete(start,sb.length());
                    sb.delete(0,end);
                }else{
                    sb.delete(start,end);
                }
                operateEnum = OperateEnum.CUT;
            }else if(OperateEnum.INSERT.getValue().equalsIgnoreCase(sequenceOperate.getType())){
                if(isRollBack && start>end){
                    String insertSequence = sequenceOperate.getSequence().substring(0,end);
                    String insertSequenceA = sequenceOperate.getSequence().substring(end);
                    sb.insert(0,insertSequence);
                    sb.append(insertSequenceA);
                }else{
                                         sb.insert(start,sequenceOperate.getSequence());
                    if(!isRollBack) {
                        operateEnum = OperateEnum.INSERT;
                        param.setEnd(param.getStart()+sequenceOperate.getSequence().length());
                        param.setSequenceLength(sequenceOperate.getSequence().length());
                    }
                }

            }else if(OperateEnum.REPLACE.getValue().equalsIgnoreCase(sequenceOperate.getType())){
                if(isRollBack && start>end) {
                    String insertSequence = sequenceOperate.getSequence().substring(0, end);
                    String insertSequenceA = sequenceOperate.getSequence().substring(end);
                    sb.delete(0,sequenceOperate.getPreSequence().length());
                    sb.insert(0, insertSequence);
                    sb.append(insertSequenceA);
                }else{
                                         if(Boolean.TRUE.equals(sequenceOperate.getNeedSetPreSequence())) {
                        if(start>end){
                            String preS = sb.substring(0,end)+sb.substring(start,sb.length());
                            sequenceOperate.setPreSequence(preS);
                        }else{
                            sequenceOperate.setPreSequence(sb.substring(start, end));
                        }

                    }
                    if(start>end){
                        sb.delete(start,sb.length());
                        sb.replace(0,end,sequenceOperate.getSequence());
                    }else{
                        sb.replace(start,end,sequenceOperate.getSequence());
                    }
                    if(!isRollBack) {
                        operateEnum = OperateEnum.REPLACE;
                                                 param.setSequenceLength(sequenceOperate.getSequence().length());
                    }
                }
            }else if(isRollBack && ArrayUtils.indexOf(fileRollBackType,sequenceOperate.getType().toUpperCase())!=-1){
                                 String sourcePath = sequenceFolder+sequenceOperate.getSourcePath();
                FileReader fileReaderA = new FileReader(sourcePath);
                sb = new StringBuilder(fileReaderA.readString());
                File file = new File(sourcePath);
                file.delete();
            }
            if(!isRollBack){
                this.save(sequenceOperate);
                List<Feature> fList = featureMapper.list(new Feature().setSequenceId(sequenceId));
                featureService.changeFeatureIndex(param,operateEnum,sequenceOperate,fList,true,true);
            }
        }
        FileWriter fw = new FileWriter(sequenceFolder+sequence.getFileName());
        String str = sb.toString();
        sequenceMapper.update(new Sequence().setId(sequenceId).setBpLength(str.length()));
        Programme up =new Programme().setId(sequence.getProgrammeId()).setUpdateTime(new Date());
        up.setUpdateStaffNo(SecurityUtils.getUserId());
        programmeMapper.updateById(up);
        fw.write(str);
        return AjaxResult.success();
    }
    public void removeBySequenceIdList(List<Long> idList){
                 List<Long> list = new ArrayList<>(2000);
        for (Long id : idList) {
            list.add(id);
            if(list.size()>2000){
                sequenceOperateMapper.removeBySequenceIdList(list);
                list.clear();
            }
        }
        if(!list.isEmpty()){
            sequenceOperateMapper.removeBySequenceIdList(list);
            list.clear();
        }
    }
    private String getSequenceFolderPath(String programmeId){
        return sequencePath +"/"+programmeId+"/";
    }
}
