package com.hmzhkj.gene.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.DateUtils;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.Information;
import com.hmzhkj.gene.domain.Programme;
import com.hmzhkj.gene.domain.ProgrammeState;
import com.hmzhkj.gene.domain.dto.ProgrammeQueryParam;
import com.hmzhkj.gene.domain.vo.ProgrammeVO;
import com.hmzhkj.gene.service.*;
import com.hmzhkj.gene.domain.Sequence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Api(tags = "Scheme information")
@RestController
@RequestMapping("/programme")
@Slf4j
public class ProgrammeController extends BaseController {
    @Autowired
    private IProgrammeService programmeService;
    @Autowired
    private IProgrammeStateService programmeStateService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private DesignService designService;
    @Autowired
    private AssociatedMoleculeService associatedMoleculeService;
    @Autowired
    private BioinformaticsPipelineService bioinformaticsPipelineService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private IDesignTBProjectMembersService designTBProjectMembersService;


    @Log(title = "Design query", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Design query", notes = "Design query")
    @GetMapping(value = "/list")
    public TableDataInfo queryPageList(ProgrammeQueryParam queryParams) {
        startPage();
        List<ProgrammeVO> pageList = programmeService.queryPageList(queryParams);
        return getDataTable(pageList);
    }


    @Log(title = "New template design", businessType = BusinessType.INSERT)
    @ApiOperation(value = "New template design", notes = "New template design")
    @PostMapping("/createByTemplate")
    public AjaxResult createByTemplate(@RequestBody Programme programme){
        Map<String,String>  map = programmeService.createByTemplate(programme);
        String fileName = map.get("fileName");
        String programmeId = map.get("programmeId");
        try {
            sequenceService.cutSequenceFile(new String[]{fileName},programmeId);
        } catch (IOException e) {
            throw new ServiceException("Splitting sequence failed!");
        }
        return AjaxResult.success("Operation successful",programmeId);
    }


    @Log(title = "New blank design", businessType = BusinessType.INSERT)
    @ApiOperation(value = "New blank design", notes = "New blank design")
    @PostMapping(value = "/createBlank")
    public AjaxResult createBlank(@RequestBody Programme programme) {
        return programmeService.createBlank(programme);
    }


    @Log(title = "Design information - delete", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Design information - delete", notes = "Design information - delete")
    @DeleteMapping(value = "/deleteBatch")
    public AjaxResult deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        programmeService.deleteBatch(ids);
        return AjaxResult.success("Operation successful");
    }


    @DeleteMapping("/project/{id}")
    public AjaxResult removeProgrammeInProject(@PathVariable String id){
        return toAjax(programmeService.removeProgrammeInProjectById(id));
    }


    @Log(title = "Share design", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Share design", notes = "Share design")
    @PostMapping(value = "/share-programme")
    public AjaxResult shareProgramme(@RequestBody Programme programme) {

        try {
            programmeService.shareProgramme(programme);
            return AjaxResult.success("Share success");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }


    @Log(title = "Share design", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Share design", notes = "Share design")
    @PostMapping(value = "/share")
    public AjaxResult share(@RequestBody Programme programme) {
        programmeService.share(programme);
        return AjaxResult.success("Operation successful");
    }


    @Log(title = "Handling shared design", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Handling shared design", notes = "Handling shared design")
    @PostMapping(value = "/dispose-share-programme")
    public AjaxResult disposeShareProgramme(@RequestBody Information information) {
        String id = information.getId();
        Integer informationState = information.getInformationState();
        String newProgrammeName = information.getProgrammeName();
        Map<String, String> map = programmeService.disposeShareProgramme(id, informationState, newProgrammeName);
        if(map != null){
            String fileName = map.get("fileName");
            String programmeId = map.get("programmeId");
            try {
                sequenceService.cutSequenceFile(new String[]{fileName},programmeId);
            } catch (IOException e) {
                throw new ServiceException("Splitting sequence failed!");
            }
            return AjaxResult.success("Operation successful",programmeId);
        }
        return AjaxResult.success("Operation successful");
    }


    @Log(title = "Obtain and process shared designs", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Obtain and process shared designs", notes = "Obtain and process shared designs")
    @PostMapping(value = "/get-dispose-share-programme")
    public TableDataInfo getDisposeShareProgramme() {

        List<Information> disposeShareProgramme = programmeService.getDisposeShareProgramme();
        return getDataTable(disposeShareProgramme);
    }


    @Log(title = "Clone design", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Clone design", notes = "Clone design")
    @PostMapping(value = "/clone")
    public AjaxResult cloneProgramme(@RequestBody Programme programme) {
        programmeService.cloneProgramme(programme);
        return AjaxResult.success("Operation successful");
    }


    @Log(title = "Design Information - Add", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Design Information - Add", notes = "Design Information - Add")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody Programme programme) {

        programme.setProgrammeNumber(DateUtils.dateTimeNow());
        programme.setCreateTime(new Date());
        programme.setCreateStaffNo(SecurityUtils.getUserId());
        programme.setPossessStaff(SecurityUtils.getUserId());
        programme.setUpdateTime(new Date());
        programme.setUpdateStaffNo(SecurityUtils.getUserId());

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("programme_number", "FA_STATUS_001");
        ProgrammeState state = programmeStateService.getOne(queryWrapper, false);
        if (state != null) {
            programme.setStateId(state.getId());
        }

        programmeService.save(programme);
        return AjaxResult.success("Operation successful");
    }


    @Log(title = "Design Information - Edit", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "Design Information - Edit", notes = "Design Information - Edit")
    @PutMapping(value = "/edit")
    public AjaxResult edit(@RequestBody Programme programme) {


        programme.setUpdateStaffNo(SecurityUtils.getUserId());
        programme.setUpdateTime(new Date());
        programmeService.updateById(programme);
        return AjaxResult.success("Operation successful");

    }


    @Log(title = "Design Information - Delete by ID", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Design Information - Delete by ID", notes = "Design Information - Delete by ID")
    @DeleteMapping(value = "/delete")
    public AjaxResult delete(@RequestParam(name = "id", required = true) String id) {
        programmeService.removeById(id);
        return AjaxResult.success("Operation successful");
    }



    @ApiOperation(value = "Design Information - Query by ID", notes = "Design Information - Query by ID")
    @GetMapping(value = "/queryById")
    @Transactional
    public AjaxResult queryById(@RequestParam(name = "id", required = true) String id) {
        Programme programme = programmeService.getById(id);
        if (programme == null) {
            return AjaxResult.error("No corresponding data found");
        }
        if(programme.getMolecularPack()==null){
            programme.setMolecularPack(new HashMap<>());
        }

        programmeService.updateById(new Programme().setId(id).setViewedTime(new Date()));
        return AjaxResult.success(programme);
    }


    @GetMapping(value = "/checkById")
    public AjaxResult checkById(@RequestParam(name = "id", required = true) String id) {
        Programme programme = programmeService.getById(id);
        if (programme == null) {
            return AjaxResult.error("No corresponding design found!");
        }
        return AjaxResult.success(SecurityUtils.getUserId().equals(programme.getPossessStaff()));
    }


    @ApiOperation(value = "Design Information - Query by ID", notes = "Design Information - Query by ID")
    @GetMapping(value = "/getProgrammeAndSequence")
    @Transactional
    public AjaxResult getProgrammeAndSequence(String id,Integer removeTemFeature) {
        Programme programme;
        if(StringUtils.isEmpty(id)){
            programme = programmeService.getLastViewProgramme();
        }else{
            programme = programmeService.getById(id);
        }
        if (programme == null) {
            return AjaxResult.error("No corresponding data found");
        }
        List<Sequence> sequenceList = sequenceService.listSequence(new Sequence().setProgrammeId(programme.getId()));
        programme.setSequenceList(sequenceList);
        return AjaxResult.success(programme);
    }


    @GetMapping("/user/list")
    public AjaxResult getAllUser(){
        List<Map<String, String>> list = programmeService.getAllUser();
        return AjaxResult.success(list);
    }




    @GetMapping("/listViewed")
    public AjaxResult listViewedProgrammes(){
        return AjaxResult.success(programmeService.selectViewedProgrammes());
    }


    @GetMapping("/checkName")
    public AjaxResult checkName(String programmeName,String id){
        Boolean isExist = programmeService.checkName(programmeName,id);
        return AjaxResult.success(isExist);
    }


    @PostMapping("/toLab")
    public AjaxResult toLab(@RequestBody Map<String , String> params){


        programmeService.toLab(params);
        return AjaxResult.success("Successfully sent!");
    }


    @PostMapping("/grna")
    public AjaxResult sendGRNA(@RequestParam("configureFile") MultipartFile configure,
                               @RequestParam("vectorFile") MultipartFile vector,
                               @RequestParam(value="genomeFile",required=false ) MultipartFile genome,
                               @RequestParam("params") String params){
        String gRNA = associatedMoleculeService.gRNA(configure, vector, genome, params);
        return AjaxResult.success("success",gRNA);
    }


    @PostMapping("/primerPrediction")
    public AjaxResult primerPrediction(@RequestParam("configureFile") MultipartFile configure,
                                       @RequestParam("vectorFile") MultipartFile vector,
                                       @RequestParam(value="genomeFile",required=false ) MultipartFile genome,
                                       @RequestParam("params") String params){
        String primerPrediction = associatedMoleculeService.primerPrediction(configure, vector, genome, params);
        return AjaxResult.success("success",primerPrediction);
    }

    @PostMapping("/geneKnockout")
    public AjaxResult geneKnockout(@RequestParam("configureFile") MultipartFile configure,
                                   @RequestParam("vectorFile") MultipartFile vector,
                                   @RequestParam("kanMXFile") MultipartFile kanMX,
                                   @RequestParam(value="genomeFile",required=false ) MultipartFile genome,
                                   @RequestParam("params") String params){
        String primerPrediction = associatedMoleculeService.geneKnockout(configure, vector, kanMX,genome, params);
        return AjaxResult.success("success",primerPrediction);
    }


    @PostMapping("/other")
    public AjaxResult other(@RequestParam("configureFile") MultipartFile configure,
                            @RequestParam("params") String params){
        String other = associatedMoleculeService.other(configure, params);
        return AjaxResult.success("success",other);
    }


    @GetMapping(value = "/resultDownload")
    public ResponseEntity<?> resultDownload(String programmeId, String step) throws IOException {
        return associatedMoleculeService.resultDownload(programmeId,step);
    }

    @GetMapping(value = "/sampleFile")
    public ResponseEntity<?> sampleFile(String fileName) throws IOException {
        return associatedMoleculeService.sampleFile(fileName);
    }


    @GetMapping(value = "/checkIsCompletes")
    public AjaxResult checkIsCompletes(String programmeId, String step){
        return AjaxResult.success("success",associatedMoleculeService.checkIsCompletes(programmeId,step));
    }


    @DeleteMapping(value = "/delStepFiles")
    public AjaxResult delStepFiles(@RequestBody Map<String,Object> params){
        return AjaxResult.success("success",associatedMoleculeService.delStepFiles(params));
    }


    @PostMapping(value = "/blast2")
    public AjaxResult blast2(@RequestBody Map<String,String> params) {

        return AjaxResult.success(bioinformaticsPipelineService.blast2(params));
    }


    @PostMapping(value = "/geneExpression")
    public ResponseEntity<?> geneExpression(@RequestBody Map<String,String> params) {

        return bioinformaticsPipelineService.geneExpression(params);
    }



    @GetMapping("/listNoProjectProgrammes")
    public AjaxResult listNoProjectProgrammes(String projectId){
        return AjaxResult.success(programmeService.listNoProjectProgrammes(projectId));
    }


    @PutMapping("/updateProject")
    public AjaxResult updateProject(@RequestBody Map<String,Object> map){
        ArrayList<String> ids = (ArrayList) map.get("ids");
        String projectId = (String) map.get("projectId");
        String projectName = (String) map.get("projectName");
        Boolean flag = designTBProjectMembersService.isProjectMember(projectId);
        if(!flag){
            throw new ServiceException("success");

        }
        return programmeService.updateProject(ids.toArray(new String[0]), projectId, projectName);
    }
}
