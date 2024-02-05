package com.hmzhkj.gene.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.ModelInfo;
import com.hmzhkj.gene.service.IModelInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Api(tags = "Model information")
@RestController
@RequestMapping("/model")
@Slf4j
public class ModelInfoController extends BaseController {
    @Autowired
    private IModelInfoService modelInfoService;


    @ApiOperation(value = "Model information - paging list query", notes = "Model information - paging list query")
    @GetMapping(value = "/list/page")
    public TableDataInfo queryPageList(ModelInfo modelInfo,
                                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<ModelInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 1);
        String modelName = modelInfo.getModelName();
        Date startDate = modelInfo.getStartDate();
        Date endDate = modelInfo.getEndDate();
        Integer[] states = Convert.toIntArray(modelInfo.getStates());
        if(modelName != null && StringUtils.hasLength(modelName)){
            queryWrapper.like("model_name",modelName);
        }
        if(startDate != null && endDate != null){
            queryWrapper.between("update_time",startDate,endDate);
        }
        if(states != null && states.length >= 1){
            queryWrapper.in("model_state", Arrays.asList(states));
        }
        Page<ModelInfo> page = new Page<ModelInfo>(pageNo, pageSize);
        IPage<ModelInfo> pageList = modelInfoService.page(page, queryWrapper);
        List<ModelInfo> list = pageList.getRecords();
        return getDataTableCustom(list, pageList.getTotal());
    }


    @ApiOperation(value = "Model information - query all data", notes = "Model information - query all data")
    @GetMapping(value = "/list")
    public AjaxResult queryList() {
        QueryWrapper<ModelInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 1);
        return AjaxResult.success(modelInfoService.list(queryWrapper));
    }


    @Log(title = "Model information - add", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Model information - add", notes = "Model information - add")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody ModelInfo modelInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("number", modelInfo.getNumber());
        if (modelInfoService.count(queryWrapper) > 0) {
            return AjaxResult.error("Model code already exists");
        }
        // modelInfo.setModelState(1);
        modelInfo.setNumber(UUID.randomUUID().toString().replaceAll("-", ""));
        modelInfo.setIsDelete(1);
        modelInfo.setCreateTime(new Date());
        modelInfo.setUpdateTime(new Date());
        modelInfo.setCreateStaffNo(SecurityUtils.getUserId());
        modelInfo.setUpdateStaffNo(SecurityUtils.getUserId());
        modelInfoService.save(modelInfo);
        return AjaxResult.success("Operation successful");
    }


    @Log(title = "Model information - Edit", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "Model information - Edit", notes = "Model information - Edit")
    @PutMapping(value = "/edit")
    public AjaxResult edit(@RequestBody ModelInfo modelInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("number", modelInfo.getNumber());
        queryWrapper.ne("id", modelInfo.getId());
        if (modelInfoService.count(queryWrapper) > 0) {
            return AjaxResult.error("Model code already exists");
        }

        modelInfo.setUpdateStaffNo(SecurityUtils.getUserId());
        modelInfo.setUpdateTime(new Date());
        modelInfoService.updateById(modelInfo);
        return AjaxResult.success("Operation successful");
    }


    @Log(title = "Model information - delete by ID", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Model information - delete by ID", notes = "Model information - delete by ID")
    @DeleteMapping(value = "/delete")
    public AjaxResult delete(@RequestParam(name = "id", required = true) String id) {
        ModelInfo data = modelInfoService.getById(id);
        if (data != null) {
            data.setIsDelete(0);
            data.setUpdateStaffNo(SecurityUtils.getUserId());
            data.setUpdateTime(new Date());
            modelInfoService.updateById(data);
        }
        return toAjax(true);
    }


    @Log(title = "Model information - batch deletion", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Model information - batch deletion", notes = "Model information - batch deletion")
    @DeleteMapping(value = "/deleteBatch")
    public AjaxResult deleteBatch(@RequestParam(name = "idsArray", required = true) String[] idsArray) {
        for (int i = 0; i < idsArray.length; i++) {
            ModelInfo data = modelInfoService.getById(idsArray[i]);
            if (data != null) {
                data.setIsDelete(0);
                data.setUpdateStaffNo(SecurityUtils.getUserId());
                data.setUpdateTime(new Date());
                modelInfoService.updateById(data);
            }
        }
        return AjaxResult.success("Operation successful");
    }


    @ApiOperation(value = "Model information - query by ID", notes = "Model information - query by ID")
    @GetMapping(value = "/queryById")
    public AjaxResult queryById(@RequestParam(name = "id", required = true) String id) {
        ModelInfo modelInfo = modelInfoService.getById(id);
        if (modelInfo == null) {
            return AjaxResult.error("Model information does not exist");
        }
        return AjaxResult.success(modelInfo);
    }
}
