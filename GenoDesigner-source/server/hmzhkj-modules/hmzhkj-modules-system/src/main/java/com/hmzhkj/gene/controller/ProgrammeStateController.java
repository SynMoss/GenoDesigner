package com.hmzhkj.gene.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.ProgrammeState;
import com.hmzhkj.gene.service.IProgrammeStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Api(tags = "Plan Status Table")
@RestController
@RequestMapping("/programmestate")
@Slf4j
public class ProgrammeStateController extends BaseController {
    @Autowired
    private IProgrammeStateService programmeStateService;


    @ApiOperation(value = "Scheme Status Table - Pagination List Query", notes = "Scheme Status Table - Pagination List Query")
    @GetMapping(value = "/list/page")
    public TableDataInfo queryPageList(ProgrammeState programmeState,
                                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<ProgrammeState> queryWrapper = new QueryWrapper<>();
        Integer[] states = Convert.toIntArray(programmeState.getStates());
        if(StringUtils.isNotEmpty(programmeState.getProgrammeName())){
            queryWrapper.like("programme_name",programmeState.getProgrammeName());
        }
        if(programmeState.getStartDate()!=null){
            queryWrapper.between("update_time",programmeState.getStartDate(),programmeState.getEndDate());
        }
        if(states != null && states.length >= 1){
            queryWrapper.in("programme_state", Arrays.asList(states));
        }
        Page<ProgrammeState> page = new Page<ProgrammeState>(pageNo, pageSize);
        IPage<ProgrammeState> pageList = programmeStateService.page(page, queryWrapper);
        List<ProgrammeState> list = pageList.getRecords();
        return getDataTableCustom(list, pageList.getTotal());
    }


    @Log(title = "Scheme Status Table - Add", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Scheme Status Table - Add", notes = "Scheme Status Table - Add")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody ProgrammeState programmeState) {
        programmeState.setProgrammeNumber(UUID.randomUUID().toString().replaceAll("-", ""));
        programmeState.setIsDelete(1);
        programmeState.setCreateTime(new Date());
        programmeState.setUpdateTime(new Date());
        programmeState.setCreateStaffNo(SecurityUtils.getUserId());
        programmeState.setUpdateStaffNo(SecurityUtils.getUserId());
        programmeStateService.save(programmeState);
        return toAjax(true);
    }


    @Log(title = "Scheme Status Table - Edit", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "Scheme Status Table - Edit", notes = "Scheme Status Table - Edit")
    @PutMapping(value = "/edit")
    public AjaxResult edit(@RequestBody ProgrammeState programmeState) {
        programmeState.setUpdateStaffNo(SecurityUtils.getUserId());
        programmeState.setUpdateTime(new Date());
        programmeStateService.updateById(programmeState);
        return toAjax(true);
    }


    @Log(title = "Scheme Status Table - Delete by ID", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Scheme Status Table - Delete by ID", notes = "Scheme Status Table - Delete by ID")
    @DeleteMapping(value = "/delete/{id}")
    public AjaxResult delete(@PathVariable String id) {
        programmeStateService.removeById(id);
        return toAjax(true);
    }


    @Log(title = "Scheme Status Table - Batch Delete", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Scheme Status Table - Batch Delete", notes = "Scheme Status Table - Batch Delete")
    @DeleteMapping(value = "/deleteBatch/{ids}")
    public AjaxResult deleteBatch(@PathVariable String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            programmeStateService.removeById(ids[i]);
        }
        return toAjax(true);
    }


    @ApiOperation(value = "Scheme Status Table - Query by ID", notes = "Scheme Status Table - Query by ID")
    @GetMapping(value = "/queryById")
    public AjaxResult queryById(@RequestParam(name = "id", required = true) String id) {
        ProgrammeState programmeState = programmeStateService.getById(id);
        if (programmeState == null) {
            return toAjax(false);
        }
        return AjaxResult.success(programmeState);
    }


    @ApiOperation(value = "Model Information - Query All Data", notes = "Model Information - Query All Data")
    @GetMapping(value = "/list")
    public AjaxResult queryList() {
        QueryWrapper<ProgrammeState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("programme_state", 1);
        queryWrapper.eq("is_delete", 1);
        queryWrapper.orderByAsc("create_time");
        return AjaxResult.success(programmeStateService.list(queryWrapper));
    }
}
