package com.hmzhkj.system.controller;

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
import com.hmzhkj.system.domain.SysReleateAccount;
import com.hmzhkj.system.service.ISysReleateAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Api(tags = "Related accounts")
@RestController
@RequestMapping("/releateaccount")
@RequiredArgsConstructor
@Slf4j
public class SysReleateAccountController extends BaseController {
    @Autowired
    private ISysReleateAccountService sysReleateAccountService;


    @ApiOperation(value = "Associated Account - All Data Query", notes = "Associated Account - All Data Query")
    @GetMapping(value = "/listall")
    public AjaxResult queryAllList(SysReleateAccount sysReleateAccount) {
        QueryWrapper<SysReleateAccount> queryWrapper = new QueryWrapper<SysReleateAccount>();
        queryWrapper.orderByDesc("create_time");
        List<SysReleateAccount> list = sysReleateAccountService.list(queryWrapper);
        return AjaxResult.success(list);
    }


    @ApiOperation(value = "Associated Account - Pagination List Query", notes = "Associated Account - Pagination List Query")
    @GetMapping(value = "/list")
    public TableDataInfo queryPageList(SysReleateAccount sysReleateAccount,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<SysReleateAccount> queryWrapper = new QueryWrapper<SysReleateAccount>();
        Page<SysReleateAccount> page = new Page<SysReleateAccount>(pageNo, pageSize);
        IPage<SysReleateAccount> pageList = sysReleateAccountService.page(page, queryWrapper);
        List<SysReleateAccount> list = pageList.getRecords();
        return getDataTableCustom(list, pageList.getTotal());
    }


    @Log(title = "Associated Account - Add", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Associated Account - Add", notes = "Associated Account - Add")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody SysReleateAccount sysReleateAccount) {
        sysReleateAccountService.save(sysReleateAccount);
        return AjaxResult.success("Added successfully！");
    }


    @Log(title = "Associated Account - Edit", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "Associated Account - Edit", notes = "Associated Account - Edit")
    @PutMapping(value = "/edit")
    public AjaxResult edit(@RequestBody SysReleateAccount sysReleateAccount) {
        sysReleateAccountService.updateById(sysReleateAccount);
        return AjaxResult.success("Edit successful！");
    }


    @Log(title = "Associated Account - Delete by ID", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Associated Account - Delete by ID", notes = "Associated Account - Delete by ID")
    @DeleteMapping(value = "/delete")
    public AjaxResult delete(@RequestParam(name = "id", required = true) String id) {
        sysReleateAccountService.removeById(id);
        return AjaxResult.success("Delete successful！");
    }


    @Log(title = "Associated Account - Batch Delete", businessType = BusinessType.DELETE)
    @ApiOperation(value = "Associated Account - Batch Delete", notes = "Associated Account - Batch Delete")
    @DeleteMapping(value = "/deleteBatch")
    public AjaxResult deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.sysReleateAccountService.removeByIds(Arrays.asList(ids.split(",")));
        return AjaxResult.success("Batch deletion successful！");
    }


    @ApiOperation(value = "Associated Account - Query by ID", notes = "Associated Account - Query by ID")
    @GetMapping(value = "/queryById")
    public AjaxResult queryById(@RequestParam(name = "id", required = true) String id) {
        SysReleateAccount sysReleateAccount = sysReleateAccountService.getById(id);
        if (sysReleateAccount == null) {
            return AjaxResult.error("No corresponding data found");
        }
        return AjaxResult.success(sysReleateAccount);
    }


    @Log(title = "Associated Account - Bind Account", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Associated Account - Bind Account", notes = "Associated Account - Bind Account")
    @PostMapping(value = "/bind")
    public AjaxResult bind(@RequestBody SysReleateAccount sysReleateAccount) {
        if (StringUtils.isEmpty(sysReleateAccount.getId())) {
            return AjaxResult.error("System ID cannot be empty！");
        }

        SysReleateAccount data = sysReleateAccountService.getById(sysReleateAccount.getId());
        if (data == null) {
            return AjaxResult.error("The system does not exist");
        }

        if (StringUtils.isEmpty(sysReleateAccount.getBindAccount())) {
            return AjaxResult.error("Binding username cannot be empty！");
        }

        if (StringUtils.isEmpty(sysReleateAccount.getBindPassword())) {
            return AjaxResult.error("Binding username cannot be empty!");
        }

        sysReleateAccount.setBindStatus(1);
        sysReleateAccount.setUpdateTime(new Date());
        sysReleateAccount.setBindTime(new Date());
        sysReleateAccount.setUpdateBy(SecurityUtils.getUsername());
        sysReleateAccountService.updateById(sysReleateAccount);
        return AjaxResult.success("Binding successful！");
    }


    @Log(title = "Associated Account - Unbind Account", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Associated Account - Unbind Account", notes = "Associated Account - Unbind Account")
    @PostMapping(value = "/unbind")
    public AjaxResult unbind(@RequestBody SysReleateAccount sysReleateAccount) {
        if (StringUtils.isEmpty(sysReleateAccount.getId())) {
            return AjaxResult.error("System ID cannot be empty！");
        }

        SysReleateAccount data = sysReleateAccountService.getById(sysReleateAccount.getId());
        if (data == null) {
            return AjaxResult.error("The system does not exist");
        }

        data.setUpdateTime(new Date());
        data.setBindTime(null);
        data.setBindAccount(null);
        data.setBindPassword(null);
        sysReleateAccountService.updateById(data);
        return AjaxResult.success("Unbind successful！");
    }
}
