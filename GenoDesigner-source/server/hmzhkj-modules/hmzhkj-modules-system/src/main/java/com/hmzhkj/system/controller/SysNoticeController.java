package com.hmzhkj.system.controller;

import java.util.List;

import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.annotation.InnerAuth;
import com.hmzhkj.system.domain.SysNotice;
import com.hmzhkj.system.domain.SysNoticeSend;
import com.hmzhkj.system.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.service.ISysNoticeService;

 
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class SysNoticeController extends BaseController
{
    private final ISysNoticeService noticeService;

     
    @RequiresPermissions("system:notice:list")
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice)
    {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }
    @PutMapping("/updateSend")
    public AjaxResult updateSend(@RequestBody SysNoticeSend noticeSend)
    {
        return noticeService.updateNoticeSend(noticeSend);
    }
    @GetMapping("/list/self")
    public TableDataInfo listSelf(SysNoticeSend noticeSend)
    {
        startPage();
        noticeSend.setUserId(SecurityUtils.getUserId());
        List<SysNoticeSend> list = noticeService.listNoticeSelf(noticeSend);
        return getDataTable(list);
    }

     
    @RequiresPermissions("system:notice:query")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable String noticeId)
    {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

     
    @RequiresPermissions("system:notice:add")
    @Log(title = "Notification Announcement", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice)
    {
        return toAjax(noticeService.insertNotice(notice));
    }
    @InnerAuth
    @PostMapping("/send")
    public R<LoginUser> send(@RequestBody SysNotice notice)
    {
        if(StringUtils.isEmpty(notice.getNoticeTitle())){
            return R.fail("Title cannot be empty");
        }
        if (notice.getUserIdList()==null ||notice.getUserIdList().isEmpty())
        {
            return R.fail("The specified user cannot be empty");
        }
        noticeService.insertNotice(notice);
        return R.ok();
    }
     
    @RequiresPermissions("system:notice:edit")
    @Log(title = "Notification Announcement", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice)
    {
        notice.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(noticeService.updateNotice(notice));
    }

     
         @Log(title = "Notification Announcement", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable String[] noticeIds)
    {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
