package com.hmzhkj.gene.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.FileUploadUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.constant.OperateEnum;
import com.hmzhkj.gene.constant.PackTypeEnum;
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
import com.hmzhkj.gene.util.JsonToFasta;
import com.hmzhkj.gene.util.JsonToGenbank;
import com.hmzhkj.gene.util.SeqUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SequenceService {
    private final SequenceMapper sequenceMapper;
    private final FeatureMapper featureMapper;
    private final FeatureService featureService;
    private final ProgrammeMapper programmeMapper;
    private final SequenceOperateMapper sequenceOperateMapper;
    private final SequenceOperateService sequenceOperateService;
    private final AssociatedMoleculeService associatedMoleculeService;
    private final String[] fastaArray = {"fa","fna","fasta"};
    private final String[] gffArray = {"gtf","gff","gff3"};
    private final String[] gbArray = {"gbff","gb","gbk"};
    private final PyService pyService;
    private final RedisService redisService;
    private final CommonService commonService;

    private void checkEndProcess(String programmeId){
        if(!redisService.hasKey(CacheConstants.PROCESS_CUT_SEQUENCE+programmeId)){
            throw new ServiceException("Canceled");
        }

    }
    public void removeProcessCache(String programmeId){
        redisService.deleteObject(CacheConstants.PROCESS_CUT_SEQUENCE+programmeId);
    }
    public Sequence getSequenceById(Long sequenceId){
        return sequenceMapper.get(new Sequence().setId(sequenceId));
    }
    public List<Sequence> listSequenceBySequenceId(Long sequenceId){
        return sequenceMapper.listById(sequenceId);
    }

    @Transactional(rollbackFor = Exception.class)
    public AjaxResult cutSequenceFile(String[] sourceFileNames,String programmeId) throws IOException {
        redisService.setCacheObject(CacheConstants.PROCESS_CUT_SEQUENCE+programmeId,programmeId,24L, TimeUnit.HOURS);
        String sequenceFolder = commonService.getSequenceFolderPath(programmeId);

        List<String> fileNameList = new ArrayList<>(sourceFileNames.length);
        FaGffToGbParam faGffToGbParam = new FaGffToGbParam();
        String txtName = null;
        for (String s : sourceFileNames) {
            String fileName = s;
            if(fileName.endsWith(".gz")){
                fileName = FileUploadUtils.decompress(new File(sequenceFolder + fileName), sequenceFolder);
            }
            String type = fileName.substring(fileName.lastIndexOf(".")+1);

            if(type.equals("txt")){
                txtName = s;
            }else{
                if(ArrayUtil.contains(gffArray,type)){
                    faGffToGbParam.setGf(sequenceFolder+fileName);
                }else if(ArrayUtil.contains(fastaArray,type)){
                    faGffToGbParam.setFa(sequenceFolder+fileName);
                    faGffToGbParam.setFaName(fileName);
                }else{
                    fileNameList.add(fileName);
                }
            }
        }
        List<Sequence> sequenceList = new ArrayList<>();
        String mergeFileName = "";
        boolean isFa = false;
        List<Feature> gffFeatureList = null;

        if(faGffToGbParam.getFa()!=null){
            isFa = true;
            fileNameList.add(faGffToGbParam.getFaName());
            if(faGffToGbParam.getGf()!=null){
                FileReader fileReader = new FileReader(faGffToGbParam.getGf());
                String type = faGffToGbParam.getGf().substring(faGffToGbParam.getGf().lastIndexOf(".")+1);
                List<String> lineList = fileReader.readLines();
                gffFeatureList = GffToFeature.convert(lineList,type);

            }

        }
        for (String fileName : fileNameList) {

            mergeFileName = fileName;
            CutSequenceParam cutSequenceParam = new CutSequenceParam(sequenceFolder,sequenceFolder+fileName);
            if(isFa){
                sequenceList.addAll(pyService.cutFa(cutSequenceParam,fileName));
                if(gffFeatureList!=null && !gffFeatureList.isEmpty()){
                    for (Sequence sequence : sequenceList) {
                        List<Feature> features = new ArrayList<>();
                        for (Feature feature : gffFeatureList) {
                            if(sequence.getName().equals(feature.getSequenceName())){
                                features.add(feature);
                            }
                        }
                        sequence.setFeatures(features);
                    }
                }
            }else{
                sequenceList.addAll(pyService.cutGb(cutSequenceParam,fileName));
            }
        }
        if(sequenceList.isEmpty()){
            return AjaxResult.error("Parsing failed");
        }
        List<String> delPathList = coverFile(programmeId,sequenceFolder,fileNameList,mergeFileName);
        saveSequenceList(sequenceList,programmeId);

        Set<String> seqNameSet = new HashSet<>(sequenceList.size());
        for (Sequence sequence : sequenceList) {
            seqNameSet.add(sequence.getFileName());
        }
        for (Sequence sequence : sequenceList) {

            if(txtName!=null){
                sequenceOperateService.parseFromTxt(txtName,sequence,false);
            }
            sequence.setFeatures(null);
            sequence.setSequence(null);
        }

        for (String s : delPathList) {
            File f = new File(s);
            if(f.exists() && !seqNameSet.contains(f.getName())){
                f.delete();
            }
        }
        this.removeProcessCache(programmeId);
        return AjaxResult.success("Operation successful",sequenceList);
    }

    public String historyPreprocessing(String configFile,String gbFile,String programmeId){
        String ourFileName = "preprocess.txt";
        String folderPath = commonService.getSequenceFolderPath(programmeId);
        ConvertHistoryParam convertHistoryParam = new ConvertHistoryParam();
        convertHistoryParam.setConfig(folderPath+configFile).setGb(folderPath+gbFile);
        convertHistoryParam.setOutdir(folderPath+ourFileName);
        pyService.convertHistory(convertHistoryParam);
        return ourFileName;
    }

    public AjaxResult checkChooseFileSequence(List<Sequence> sequenceList){
        Sequence firstSequence = sequenceList.get(0);
        String sourceFileName = firstSequence.getSourceFileName();
        Programme programme = programmeMapper.selectById(firstSequence.getProgrammeId());
        List<Annotation> annotationList = programme.getAnnotationList();
        Map<String,Set<String>> map = new HashMap<>();
        for (Sequence sequence : sequenceList) {
            Set<String> set;
            if(map.containsKey(sequence.getSourceFileName())){
                set = map.get(sequence.getSourceFileName());
            }else{
                set = new TreeSet<>();
                map.put(sequence.getSourceFileName(),set);
            }
            set.add(sequence.getName()+"_"+sequence.getBpLength());
        }
        Set<String> firstFile = map.get(sourceFileName);
        Sequence updateObj = new Sequence().setProgrammeId(firstSequence.getProgrammeId()).setIsValid(0);
        for (Map.Entry<String, Set<String>> stringListEntry : map.entrySet()) {
            Set<String> set = stringListEntry.getValue();
            int isValid = 1;
            if(firstFile.size()!=set.size()){
                isValid = 0;
            }
            if(isValid==1){
                for (String s : firstFile) {
                    if(!set.contains(s)){
                        isValid = 0;
                        break;
                    }
                }
            }
            Annotation a = new Annotation(stringListEntry.getKey());
            int aIndex = annotationList.indexOf(a);
            Annotation annotation = annotationList.get(aIndex);
            annotation.setIsValid(isValid);
        }
        Programme updateProgramme = new Programme().setAnnotationList(annotationList);
        updateProgramme.setChooseFile(1).setId(firstSequence.getProgrammeId());
        programmeMapper.updateById(updateProgramme);
        return AjaxResult.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public AjaxResult chooseFile(String programmeId,String sourceFileName){
        String sequenceFolder = commonService.getSequenceFolderPath(programmeId);
        List<Sequence> list = sequenceMapper.listToDelSequence(new Sequence().setProgrammeId(programmeId).setSourceFileName(sourceFileName));
        List<Long> sequenceIdList = new ArrayList<>();
        List<String> delPathList = new ArrayList<>();
        for (Sequence sequence : list) {
            sequenceIdList.add(sequence.getId());
            delPathList.add(sequenceFolder+sequence.getSourceFileName());
            delPathList.add(sequenceFolder+sequence.getFileName());
        }

        this.removeByIdList(sequenceIdList);

        featureService.removeBySequenceIdList(sequenceIdList,null);

        programmeMapper.updateById(new Programme().setId(programmeId).setChooseFile(0).setSourceFileName(sourceFileName).setSourceFileMergeName(sourceFileName));

        for (String s : delPathList) {
            File f = new File(s);
            if(f.exists()){
                f.delete();
            }
        }
        return AjaxResult.success("Operation successful");
    }
    public void removeByIdList(List<Long> idList){

        List<Long> list = new ArrayList<>(2000);
        Iterator<Long> iterator = idList.iterator();
        while (iterator.hasNext()){
            list.add(iterator.next());
            if(list.size()==2000||!iterator.hasNext()){
                sequenceMapper.removeByIdList(list);
                list.clear();
            }
        }
    }
    private void saveSequenceList(List<Sequence> sequenceList,String programmeId){
        String sourceName = "";
        String keywordsName = "";

        List<Annotation> annotationList = new ArrayList<>();
        List<Sequence> saveSequenceList = new ArrayList<>(2000);
        List<Feature> saveFeatureListAll = new ArrayList<>(20000);
        Iterator<Sequence> sequenceIterator = sequenceList.iterator();
        while (sequenceIterator.hasNext()) {
            Sequence parsedSequence = sequenceIterator.next();
            Annotation annotation = new Annotation(parsedSequence.getSourceFileName());
            int annotationIndex = annotationList.indexOf(annotation);
            if (annotationIndex != -1) {
                annotation = annotationList.get(annotationIndex);
            } else {
                String sf = annotation.getKey().substring(annotation.getKey().indexOf('-') + 1);
                annotationList.add(annotation.setSourceFileName(sf).setKeyWords(keywordsName));
            }
            parsedSequence.setProgrammeId(programmeId);
            if (StringUtils.isEmpty(keywordsName) && StringUtils.isNotEmpty(parsedSequence.getKeyWords())
                    && !".".equals(parsedSequence.getKeyWords())) {
                keywordsName = parsedSequence.getKeyWords();
            }
            saveSequenceList.add(parsedSequence);
            if (saveSequenceList.size() == 2000 || !sequenceIterator.hasNext()) {
                sequenceMapper.saveList(saveSequenceList);
                for (Sequence sequence : saveSequenceList) {
                    List<Feature> features = sequence.getFeatures();
                    if(features==null){
                        break;
                    }
                    int sort = 99;
                    for (Feature feature : features) {
                        feature.setSequenceId(sequence.getId());
                        feature.setIsTemporary(false);
                        if ("source".equals(feature.getType())) {
                            continue;
                        }
                        if (StringUtils.isNotEmpty(feature.getSource())) {
                            if (StringUtils.isEmpty(annotation.getSource())) {
                                annotation.setSource(feature.getSource());
                            }
                            if (StringUtils.isEmpty(sourceName)) {
                                sourceName = feature.getSource();
                            }
                        }
                        feature.setSort(sort++);
                        feature.init(sequence.getBpLength());
                        if (feature.getStart() != null) {
                            saveFeatureListAll.add(feature);
                        }
                    }
                }
                saveSequenceList.clear();
            }
        }
        List<Feature> saveFeatures = new ArrayList<>(2000);
        Iterator<Feature> featureIterator = saveFeatureListAll.iterator();
        while (featureIterator.hasNext()){
            saveFeatures.add(featureIterator.next());
            if(saveFeatures.size()==2000||!featureIterator.hasNext()){
                this.checkEndProcess(programmeId);
                featureMapper.saveList(saveFeatures);
                saveFeatures.clear();
            }
        }
        Programme programme = new Programme().setId(programmeId).setUpdateTime(new Date());
        programme.setUpdateStaffNo(SecurityUtils.getUserId()).setChooseFile(0).setAnnotationList(annotationList);
        if(StringUtils.isNotEmpty(sourceName)){
            programme.setDatabaseForAnnotation(sourceName);
        }else if(StringUtils.isNotEmpty(keywordsName)){
            programme.setDatabaseForAnnotation(keywordsName);
        }
        programmeMapper.updateById(programme);
    }
    private List<String> coverFile(String programmeId,String sequenceFolder,List<String> fileNameList,String mergeFileName){
        Programme programme = programmeMapper.selectById(programmeId);
        List<Sequence> sourceSequenceList = sequenceMapper.list(new Sequence().setProgrammeId(programmeId));
        List<String> delPathList = new ArrayList<>();
        if(!sourceSequenceList.isEmpty()){
            String[] sourceNames = programme.getSourceFileName().split(",");

            for (String sourceName : sourceNames) {
                if(fileNameList.indexOf(sourceName)==-1){
                    delPathList.add(sequenceFolder+sourceName);
                }
            }
            if(StringUtils.isNotEmpty(programme.getPackFileName())){
                delPathList.add(sequenceFolder+programme.getPackFileName());
            }
            List<Long> sequenceIdList = new ArrayList<>();
            for (Sequence sequence : sourceSequenceList) {
                sequenceIdList.add(sequence.getId());
                String fileName = sequenceFolder+sequence.getFileName();
                delPathList.add(fileName);
            }
            sequenceOperateService.removeBySequenceIdList(sequenceIdList);
            featureService.removeBySequenceIdList(sequenceIdList,null);
            this.removeByIdList(sequenceIdList);
        }
        Programme updateProgramme = new Programme().setId(programmeId).setSourceFileName(CollectionUtil.join(fileNameList,","));
        updateProgramme.setDatabaseForAnnotation(updateProgramme.getSourceFileNameShow()).setSourceFileMergeName(mergeFileName);
        programmeMapper.updateById(updateProgramme);
        return delPathList;
    }
    public List<Sequence> listSequence(Sequence sequence){
        return sequenceMapper.list(sequence);
    }
    public AjaxResult search(String str,Long id,String featureName){
        if(StringUtils.isNotEmpty(str)){
            str = str.toUpperCase();
        }
        Sequence sequence = sequenceMapper.get(new Sequence().setId(id));
        int sequenceLength = 30000;
        Programme programme = programmeMapper.selectById(sequence.getProgrammeId());
        if(programme.getSequenceLengthToShow()!=null){
            sequenceLength = programme.getSequenceLengthToShow();
        }
        String sequenceFolder = commonService.getSequenceFolderPath(sequence.getProgrammeId());
        FileReader fileReader = new FileReader(sequenceFolder+sequence.getFileName());
        String sourceStr = fileReader.readString().toUpperCase();
        Map<String,Integer> selection = new HashMap<>();
        int start;
        List<Map<String,Integer>> searchList = new ArrayList<>();
        sequence.setSearchList(searchList);

        if(StringUtils.isNotEmpty(featureName)){
            List<Feature> featureList = featureMapper.list(new Feature().setSequenceId(id).setName(featureName));
            if(featureList.isEmpty()){
                return AjaxResult.error("Not matched to sequence!");
            }
            Feature feature = featureList.get(0);
            start = feature.getStart();
            selection.put("start",start);
            int endLength = feature.getEnd() - start;
            if(endLength>sequenceLength){
                endLength = sequenceLength;
            }
            selection.put("end",start+endLength);
            for (Feature f : featureList) {
                Map<String,Integer> map = new HashMap<>();

                map.put("start",f.getStart()+1);
                int endLengthA = f.getEnd() - f.getStart();
                if(endLengthA>sequenceLength){
                    endLengthA = sequenceLength;
                }
                map.put("end",f.getStart()+endLengthA);
                searchList.add(map);
            }
        }else{
            if(str.length()>sequenceLength){
                return AjaxResult.error("Sequence super long");
            }
            String strReverseComplement = SeqUtil.reverseComplement(str);
            start = sourceStr.indexOf(str);
            int startReverseComplement = sourceStr.indexOf(strReverseComplement);
            if(start==-1 && startReverseComplement==-1){
                return AjaxResult.error("Not matched!");
            }
            int strand = 1;
            if(start==-1){
                start = startReverseComplement;
                strand=-1;
            }

            selection.put("start",start);
            selection.put("end",start+str.length()-1);

            int a = start;
            while (a != -1) {

                Map<String,Integer> map = new HashMap<>();

                map.put("start",a+1);
                map.put("end",a+str.length());
                map.put("strand",strand);
                searchList.add(map);
                a = sourceStr.indexOf(str, a + 1);
            }

            if(strand==1){
                int b = startReverseComplement;
                while (b != -1) {

                    Map<String,Integer> map = new HashMap<>();

                    map.put("start",b+1);
                    map.put("end",b+str.length());
                    map.put("strand",-1);
                    searchList.add(map);
                    b = sourceStr.indexOf(strReverseComplement, b + 1);
                }
                searchList.sort(Comparator.comparingInt(o -> o.get("start")));
            }

            if(sourceStr.length()<sequenceLength){
                sequence.setSequence(sourceStr).setStart(0);
            }
        }

        if(StringUtils.isEmpty(sequence.getSequence())){
            int length = start+sequenceLength;
            if(sourceStr.length()<length){
                length = sourceStr.length();
            }

            if(start>sourceStr.length()-sequenceLength-1 && sourceStr.length()>sequenceLength){
                start = sourceStr.length()-sequenceLength-1;
            }
            String resultStr = sourceStr.substring(start,length);
            sequence.setSequence(resultStr).setStart(start);
        }
        PageHelper.startPage(1,programme.getMaxFeaturesToShowNoNull());
        List<Feature> featureList = featureMapper.list(new Feature().setSequenceId(sequence.getId()).setStart(sequence.getStart()).setEnd(sequence.getStart()+sequenceLength));
        sequence.setFeatures(featureList);
        return AjaxResult.success("query was successful",sequence.setSelection(selection));
    }
    public AjaxResult getSequence(Sequence queryParam) throws IOException {
        if(queryParam.getId()==null){
            return AjaxResult.error("parameter error");
        }
        Integer start = queryParam.getStart();
        if(start==null || start<0){
            start = 0;
        }
        Sequence sequence = sequenceMapper.get(new Sequence().setId(queryParam.getId()));
        if(start>=sequence.getBpLength()){
            return AjaxResult.error("Out of range");
        }
        String sequenceFolder = commonService.getSequenceFolderPath(sequence.getProgrammeId());
    
        String path = sequenceFolder+sequence.getFileName();
        java.io.FileReader fileReader = new java.io.FileReader(path);
        int sequenceLength = 30000;
        Programme programme = programmeMapper.selectById(sequence.getProgrammeId());
        if(programme.getSequenceLengthToShow()!=null){
            sequenceLength = programme.getSequenceLengthToShow();
        }
        char[] chars = new char[sequenceLength];
        fileReader.skip(start);
        int count = fileReader.read(chars);
        fileReader.close();
        if(count==-1){
            return AjaxResult.error("File read error");
        }

        String s = new String(chars,0,count);
        sequence.setSequence(s).setStart(start);
        PageHelper.startPage(1,programme.getMaxFeaturesToShowNoNull());
        List<Feature> featureList = featureMapper.list(new Feature().setSequenceId(sequence.getId()).setStart(sequence.getStart()).setEnd(sequence.getStart()+sequenceLength));
        sequence.setFeatures(featureList);
        return AjaxResult.success("query was successful",sequence);
    }
    private void loadFeature(Sequence sequence){
        Programme programme = programmeMapper.selectById(sequence.getProgrammeId());
        int featureLimits = programme.getMaxFeaturesToShowNoNull();

        List<String> listSource = featureMapper.listSource(sequence.getId());
        int sequenceLength = sequence.getSequenceLengthToShow();
        if(sequenceLength==0){
            sequenceLength=100;
        }
        List<Feature> featureList = featureMapper.list(new Feature().setSequenceId(sequence.getId()).setStart(sequence.getStart()).setEnd(sequence.getStart()+sequenceLength));
        Map<String,List<Feature>> sourceFeature = new HashMap<>();
        for (String s : listSource) {
            List<Feature> fList = new ArrayList<>();
            sourceFeature.put(s,fList);
        }
        List<Feature> noSourceFeatureList = new ArrayList<>();
        for (Feature feature : featureList) {
            if(StringUtils.isNotEmpty(feature.getSource())){
                List<Feature> fList = sourceFeature.get(feature.getSource());
                if(fList.size()<featureLimits){
                    fList.add(feature);
                }
            }else{
                if(noSourceFeatureList.size()<featureLimits) {

                    noSourceFeatureList.add(feature);
                }
            }
        }

        if(sourceFeature.isEmpty() || noSourceFeatureList.size()>0) {
            sourceFeature.put(programme.getSourceFileNameShow(),noSourceFeatureList);
        }
        String[] keys = new String[sourceFeature.keySet().size()];
        sequence.setSourceFeatureKeys(sourceFeature.keySet().toArray(keys));
        sequence.setSourceFeature(sourceFeature);
    }


    public AjaxResult pack(String programmeId,List<String> packTypeList) {
        Programme programme = programmeMapper.selectById(programmeId);

        String sequenceFolder = commonService.getSequenceFolderPath(programmeId);

        String gbffName = programme.getProgrammeName()+".gbff";
        String fastaName = programme.getProgrammeName()+".fasta";
        String historyName = programme.getProgrammeName()+"_history.txt";
        String zipName = programme.getProgrammeName()+".zip";
        List<Sequence> sequenceList = sequenceMapper.list(new Sequence().setProgrammeId(programmeId));
        if(sequenceList.isEmpty()){
            return AjaxResult.error("parse error");
        }
        List<String> lineGenBankList = new ArrayList<>();
        List<String> lineFastaList = new ArrayList<>();
        List<String> lineHistoryList = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        for (Sequence sequence : sequenceList) {
            sequence.setFeatures(featureMapper.list(new Feature().setSequenceId(sequence.getId())));
            FileReader fileReader = new FileReader(sequenceFolder+sequence.getFileName());
            String s = fileReader.readString();
            sequence.setSequence(s);

            lineGenBankList.addAll(JsonToGenbank.convert(sequence));
            lineFastaList.addAll(JsonToFasta.convert(sequence));
            List<SequenceOperate> list = sequenceOperateMapper.list(new SequenceOperate().setSequenceId(sequence.getId()));
            list.sort(Comparator.comparingInt(SequenceOperate::getOrder));
            for (SequenceOperate sequenceOperate : list) {
                int start = sequenceOperate.getStart()==null?-1:sequenceOperate.getStart();

                int end = sequenceOperate.getEnd()==null?-1:sequenceOperate.getEnd()-1;
                StringBuilder sb = new StringBuilder(sequence.getName());
                sb.append("\t").append("-").append("\t").append(start).append("\t").append(end);
                sb.append("\t").append(sequenceOperate.getType());
                sb.append("\t").append(sequenceOperate.getSequence()==null?"-":sequenceOperate.getSequence());
                sb.append("\t").append("-");
                lineHistoryList.add(sb.toString());
            }
        }

        File gbffFile = new File(sequenceFolder+gbffName);
        if(gbffFile.exists()){
            gbffFile.delete();
        }
        FileWriter gbffFileWriter = new FileWriter(gbffFile);
        gbffFileWriter.appendLines(lineGenBankList);
        File fastaFile = new File(sequenceFolder+fastaName);
        if(fastaFile.exists()){
            fastaFile.delete();
        }
        FileWriter fastaWriter = new FileWriter(fastaFile);
        fastaWriter.appendLines(lineFastaList);
        File historyFile = new File(sequenceFolder+historyName);
        if(historyFile.exists()){
            historyFile.delete();
        }
        FileWriter historyWriter = new FileWriter(historyFile);
        historyWriter.appendLines(lineHistoryList);
        if(packTypeList.contains(PackTypeEnum.GENBANK.getValue())){
            fileList.add(gbffFile);
        }
        if(packTypeList.contains(PackTypeEnum.FASTA.getValue())){
            fileList.add(fastaFile);
        }
        if(packTypeList.contains(PackTypeEnum.HISTORY.getValue())){
            fileList.add(historyFile);
        }
        File zipFile = FileUtil.file(sequenceFolder+zipName);
        if(zipFile.exists()){
            zipFile.delete();
        }
        File[] files = new File[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            files[i] = fileList.get(i);
        }


        File molecule = new File(associatedMoleculeService.getPrefixPath() + programmeId);
        File[] moleculeFiles = null;
        if(molecule.exists()){
            moleculeFiles = molecule.listFiles((pathname) -> pathname.getName().toLowerCase().endsWith(".zip"));
        }

        ZipUtil.zip(zipFile,false, ArrayUtil.addAll(files, moleculeFiles));
        programmeMapper.updateById(new Programme().setId(programmeId).setPackFileName(zipName).setPackState(0).
                setPackType(packTypeList.toString().replaceAll("\\[|\\]", "")));
        return AjaxResult.success();
    }
    public ResponseEntity<?> packDownload(String programmeId) throws IOException {
        String sequenceFolder = commonService.getSequenceFolderPath(programmeId);
        Programme programme = programmeMapper.selectById(programmeId);
        FileSystemResource fileSystemResource = new FileSystemResource(new File(sequenceFolder+programme.getPackFileName()));
        return ResponseEntity
                .ok()
                .contentLength(fileSystemResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }
    public ResponseEntity<?> excelDownload(String programmeId,String excelName) throws IOException {
        if(!excelName.endsWith(".xlsx")&&!excelName.endsWith(".txt")){
            throw new ServerException("Do not allow downloads");
        }
        String sequenceFolder = commonService.getSequenceFolderPath(programmeId);
        FileSystemResource fileSystemResource = new FileSystemResource(new File(sequenceFolder+excelName));
        return ResponseEntity
                .ok()
                .contentLength(fileSystemResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }
}
