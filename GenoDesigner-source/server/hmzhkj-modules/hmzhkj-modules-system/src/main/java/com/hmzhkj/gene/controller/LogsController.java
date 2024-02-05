package com.hmzhkj.gene.controller;

import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.gene.domain.Logs;
import com.hmzhkj.gene.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

 
@RestController
@RequestMapping("/logs")
public class LogsController extends BaseController
{
    @Autowired
    private LogsService logsService;

     
    @GetMapping("/{projectId}")
    public AjaxResult listLogs(@PathVariable String projectId)
    {
        List<Logs> list = logsService.selectLogsList(projectId);
        return AjaxResult.success(list);
    }

      

}
