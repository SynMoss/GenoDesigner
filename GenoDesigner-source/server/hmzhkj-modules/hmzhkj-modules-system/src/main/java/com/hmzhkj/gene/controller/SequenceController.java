package com.hmzhkj.gene.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.gene.constant.OperateEnum;
import com.hmzhkj.gene.constant.PyFeatureEnum;
import com.hmzhkj.gene.domain.Programme;
import com.hmzhkj.gene.domain.Sequence;
import com.hmzhkj.gene.domain.SequenceOperate;
import com.hmzhkj.gene.model.*;
import com.hmzhkj.gene.service.*;
import com.hmzhkj.gene.domain.Feature;
import com.hmzhkj.gene.util.SeqUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/sequence")
@RestController
@RequiredArgsConstructor
public class SequenceController  extends BaseController {
    private final SequenceService sequenceService;
    private final SequenceOperateService sequenceOperateService;
    private final IProgrammeService programmeService;
    private final FeatureService featureService;
    private final CommonService commonService;
    @ApiOperation(value = "Save Sequence")
    @PostMapping(value = "/save")
    @Log(title = "Save Sequence", businessType = BusinessType.INSERT)
    public AjaxResult cutSequence(@RequestBody String[] sourceFileNames,String programmeId,Integer chooseFile) throws IOException {
        AjaxResult ajaxResult = sequenceService.cutSequenceFile(sourceFileNames,programmeId);
        if(ajaxResult.isSuccess() && Integer.valueOf(1).equals(chooseFile) ){
            sequenceService.checkChooseFileSequence((List<Sequence>)ajaxResult.getData());
            ajaxResult.put("chooseFile",1);
        }
        return ajaxResult;
    }
    @Log(title = "Cancel saving sequence", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Cancel saving sequence")
    @DeleteMapping(value = "/cancel")
    public AjaxResult cancelCutSequence(String programmeId) throws IOException {
        sequenceService.removeProcessCache(programmeId);
        return AjaxResult.success();
    }
    @Log(title = "Sequence List", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Sequence List")
    @GetMapping(value = "/list")
    public AjaxResult listSequence(String programmeId) {
        return AjaxResult.success(sequenceService.listSequence(new Sequence().setProgrammeId(programmeId)));
    }
    @Log(title = "Sequence List", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Sequence List")
    @GetMapping(value = "/listBySequenceId")
    public AjaxResult listBySequenceId(Long sequenceId) {
        return AjaxResult.success(sequenceService.listSequenceBySequenceId(sequenceId));
    }
    @ApiOperation(value = "new-built feature")
    @PostMapping(value = "/saveFeatureList")
    public AjaxResult saveFeatureList(@RequestBody  List<Feature> list){
        return featureService.saveFeatureList(list);
    }
    @Log(title = " new-built/update feature", businessType = BusinessType.INSERT)
    @ApiOperation(value = "new-built/update feature")
    @PostMapping(value = "/saveOrUpdateFeature")
    public AjaxResult saveFeature(@RequestBody Feature feature){
        OperateEnum operateEnum=feature.getId()==null?OperateEnum.FEATURE_INSERT:OperateEnum.FEATURE_UPDATE;
        return sequenceOperateService.operateFeature(feature,operateEnum);
    }
    @Log(title = "delete feature", businessType = BusinessType.DELETE)
    @ApiOperation(value = "delete feature")
    @PostMapping(value = "/removeFeatureList")
    public AjaxResult removeFeatureList(@RequestBody List<Long> list,Long sequenceId){
        OperateEnum operateEnum=list.size()==1?OperateEnum.FEATURE_CUT:OperateEnum.FEATURE_CUT_LIST;
        sequenceOperateService.operateFeature(new Feature().setIdList(list).setSequenceId(sequenceId),operateEnum);
        return AjaxResult.success();
    }
    @Log(title = "Sequence List", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Sequence list, divided by file")
    @GetMapping(value = "/list/file")
    public AjaxResult listFileSequence(String programmeId,String name,Integer start) throws IOException {
        List<Sequence> list =sequenceService.listSequence(new Sequence().setProgrammeId(programmeId).setName(name));
                 Programme programme = programmeService.getById(list.get(0).getProgrammeId());
        List<Annotation> annotationList = programme.getAnnotationList();
        List<Sequence> result = new ArrayList<>();
        for (Sequence sequence : list) {
            AjaxResult ajaxResult = sequenceService.getSequence(sequence.setStart(start));
            if(ajaxResult.isSuccess()){
                Sequence seq = (Sequence) ajaxResult.getData();
                Annotation annotation = annotationList.get(annotationList.indexOf(new Annotation(seq.getSourceFileName())));
                String source;
                if(StringUtils.isNotEmpty(annotation.getSource())){
                    source = annotation.getSource();
                }else if(StringUtils.isNotEmpty(annotation.getKeyWords())){
                    source = annotation.getKeyWords();
                }else{
                    source = annotation.getSourceFileName();
                }
                result.add(seq.setSource(source));
            }else{
                return ajaxResult;
            }
        }
        return AjaxResult.success(result);
    }
    @Log(title = "Sequence details", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Sequence")
    @GetMapping(value = "/get")
    public AjaxResult getSequence(Sequence sequence) throws IOException {
        return sequenceService.getSequence(sequence);
    }
    @Log(title = "Search through strings or files", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Search through strings or files")
    @PostMapping(value = "/search")
    public AjaxResult search(Long id, MultipartFile file, String str,String featureName) throws IOException {
        if(StringUtils.isEmpty(str) && StringUtils.isEmpty(featureName)){
            InputStream is = file.getInputStream();
            str = IOUtils.toString(is,"utf-8");
            String strs[] = str.split("\r?\n");
            StringBuilder sb = new StringBuilder();
            for (String s : strs) {
                if(s.startsWith(">")){
                    continue;
                }
                sb.append(s);
            }
            str = sb.toString();
        }
        return sequenceService.search(str,id,featureName);
    }
    @Log(title = "pack", businessType = BusinessType.INSERT)
    @ApiOperation(value = "pack")
    @RequiresPermissions("programme:programme:package")
    @PostMapping(value = "/pack")
    public AjaxResult pack(@RequestBody Pack pack) {
        List<String> programmeIdList = pack.getProgrammeIdList();
        List<String> packTypeList = pack.getPackTypeList();
        if(programmeIdList==null || packTypeList==null||programmeIdList.isEmpty()||packTypeList.isEmpty()){
            return AjaxResult.error("parameter error");
        }
        for (String programmeId : programmeIdList) {
            sequenceService.pack(programmeId,packTypeList);
        }
        return AjaxResult.success();
    }
    @Log(title = "choose file", businessType = BusinessType.INSERT)
    @ApiOperation(value = "choose file")
    @PostMapping(value = "/chooseFile")
    public AjaxResult chooseFile(@RequestBody Programme programme) {
        return sequenceService.chooseFile(programme.getId(),programme.getSourceFileName());
    }
    @Log(title = "Package Download", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Package Download")
    @RequiresPermissions("programme:programme:download")
    @GetMapping(value = "/pack/download")
    public ResponseEntity<?> packDownload(String programmeId) throws IOException {
        return sequenceService.packDownload(programmeId);
    }
    @ApiOperation(value = "Package Download")
    @GetMapping(value = "/errExcel/download")
    public ResponseEntity<?> excelDownload(String programmeId,String excelPath) throws IOException {
        return sequenceService.excelDownload(programmeId,excelPath);
    }
    @Log(title = "action list", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "action list")
    @GetMapping(value = "/operate/list")
    public TableDataInfo listSequenceOperate(Long sequenceId) {
        startPage();
        List<SequenceOperate> list =sequenceOperateService.listSequenceOperate(new SequenceOperate().setSequenceId(sequenceId));
        return getDataTable(list);
    }

    @Log(title = "Save sequence operation", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Save operation")
    @PostMapping(value = "/operate")
    public AjaxResult saveOperate(@RequestBody SequenceOperate sequenceOperate) {
        if(!OperateEnum.CUT.getValue().equalsIgnoreCase(sequenceOperate.getType())&&
                !OperateEnum.INSERT.getValue().equalsIgnoreCase(sequenceOperate.getType())&&
                !OperateEnum.REPLACE.getValue().equalsIgnoreCase(sequenceOperate.getType())){
            return AjaxResult.error("Operation failed, type change not supported");
        }
        List<SequenceOperate> list = new ArrayList<>();
        list.add(sequenceOperate);
        if(sequenceOperate.getStart().equals(sequenceOperate.getEnd())){
            sequenceOperate.setEnd(sequenceOperate.getEnd()+1);
        }
        if(Integer.valueOf(-1).equals(sequenceOperate.getStrand())&&StringUtils.isNotEmpty(sequenceOperate.getSequence())){
            sequenceOperate.setSequence(SeqUtil.reverseComplement(sequenceOperate.getSequence()));
        }
        sequenceOperate.setNeedSetPreSequence(true);
        sequenceOperate.setCanRollback(true);
        sequenceOperateService.operateSequenceFile(list,sequenceOperate.getSequenceId(),false);
        return AjaxResult.success("Operation successful",sequenceOperate);
    }

           @Log(title = "rollback", businessType = BusinessType.DELETE)
    @ApiOperation(value = "rollback")
    @DeleteMapping(value = "/operate/rollback")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult rollback(Long sequenceId,Long id) {
        return sequenceOperateService.rollback(sequenceId,id);
    }
    @ApiOperation(value = " import feature")
    @PostMapping(value = "/importFeature")
    public AjaxResult importFeature(@RequestBody String[] sourceFileNames,Long sequenceId,Integer checkAll) throws IOException {
        if(Integer.valueOf(1).equals(checkAll)){
            List<Sequence> list = sequenceService.listSequenceBySequenceId(sequenceId);
            for (Sequence sequence : list) {
                sequenceOperateService.importFeature(sourceFileNames,sequence.getId());
            }
        }else{
            sequenceOperateService.importFeature(sourceFileNames,sequenceId);
        }
        return AjaxResult.success();
    }
    @ApiOperation(value = "Import Profile")
    @PostMapping(value = "/importConfig")
    public AjaxResult importConfig(@RequestBody String[] sourceFileNames,Long sequenceId,Integer isEditFeatureName) {
        return sequenceOperateService.parseFromTxt(sourceFileNames[0],
                sequenceService.getSequenceById(sequenceId),Integer.valueOf(1).equals(isEditFeatureName)?true:false);
    }

    @ApiOperation(value = "Compare imports feature")
    @PostMapping(value = "/importFeatureFromGb")
    public AjaxResult importFeatureFromGb(@RequestBody String[] sourceFileNames,Long sequenceId,Integer checkAll) throws IOException {
        if(Integer.valueOf(1).equals(checkAll)){
            List<Sequence> list = sequenceService.listSequenceBySequenceId(sequenceId);
            for (Sequence sequence : list) {
                sequenceOperateService.importFeatureFromGb(sourceFileNames,sequence.getId());
            }
        }else{
            sequenceOperateService.importFeatureFromGb(sourceFileNames,sequenceId);
        }
        return AjaxResult.success();
    }
    @ApiOperation(value = "removeRepeatFeature")
    @PostMapping(value = "/removeRepeatFeature")
    public AjaxResult removeRepeatFeature(@RequestBody FeatureRepeatCondition featureRepeatCondition,Integer checkAll) {
        if(Integer.valueOf(1).equals(checkAll)){
            List<Sequence> list = sequenceService.listSequenceBySequenceId(featureRepeatCondition.getSequenceId());
            for (Sequence sequence : list) {
                featureRepeatCondition.setSequenceId(sequence.getId());
                sequenceOperateService.removeRepeatFeature(featureRepeatCondition);
            }
        }else{
            sequenceOperateService.removeRepeatFeature(featureRepeatCondition);
        }
        return AjaxResult.success();
    }
    @ApiOperation(value = "removeByType")
    @PostMapping(value = "/removeByType")
    public AjaxResult removeByType(@RequestBody PyFeatureParam pyFeatureParam,Integer checkAll) throws IOException {
        pyOperateFeatureAloneOrList(pyFeatureParam,PyFeatureEnum.DELETE_FEATURE,checkAll);
        return AjaxResult.success();
    }
    @ApiOperation(value = "codonReplace")
    @PostMapping(value = "/codonReplace")
    public AjaxResult codonReplace(@RequestBody PyFeatureParam pyFeatureParam,Integer checkAll) throws IOException {
        pyOperateFeatureAloneOrList(pyFeatureParam,PyFeatureEnum.CODON_REPLACE,checkAll);
        return AjaxResult.success();
    }

    @ApiOperation(value = "mergeFeature")
    @PostMapping(value = "/mergeFeature")
    public AjaxResult mergeFeature(@RequestBody PyFeatureParam pyFeatureParam,Integer checkAll) throws IOException {
        pyOperateFeatureAloneOrList(pyFeatureParam,PyFeatureEnum.MERGE_FEATURE,checkAll);
        return AjaxResult.success();
    }
    @ApiOperation(value = "remainByType")
    @PostMapping(value = "/remainByType")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult remainByType(@RequestBody PyFeatureParam pyFeatureParam,Integer checkAll) throws IOException {
        pyOperateFeatureAloneOrList(pyFeatureParam,PyFeatureEnum.REDUCE_GENOME,checkAll);
        return AjaxResult.success();
    }
    @PostMapping("/insertSequenceByFeatureTypeEnd3")
    @ApiOperation(value = "Insert a sequence in the last three and fourth positions based on the feature type")
    public AjaxResult insertSequenceByFeatureTypeEnd3(@RequestBody PyFeatureParam pyFeatureParam,Integer checkAll) throws IOException {
        pyOperateFeatureAloneOrList(pyFeatureParam,PyFeatureEnum.GLOBAL_INSERTION,checkAll);
        return AjaxResult.success();
    }
    private void pyOperateFeatureAloneOrList(PyFeatureParam pyFeatureParam,PyFeatureEnum pyFeatureEnum,Integer checkAll) throws IOException {
        if(Integer.valueOf(1).equals(checkAll)){
            List<Sequence> list = sequenceService.listSequenceBySequenceId(pyFeatureParam.getSequenceId());
            for (Sequence sequence : list) {
                pyFeatureParam.setSequenceId(sequence.getId());
                sequenceOperateService.pyOperateFeature(pyFeatureParam,pyFeatureEnum);
            }
        }else{
            sequenceOperateService.pyOperateFeature(pyFeatureParam,pyFeatureEnum);
        }
    }
                              @ApiOperation(value = "listType")
    @GetMapping(value = "/listType")
    public AjaxResult listType(Feature feature) {
        return AjaxResult.success(featureService.listType(feature));
    }
}
