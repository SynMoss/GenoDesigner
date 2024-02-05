package com.hmzhkj.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.utils.poi.ExcelUtil;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysPost;
import com.hmzhkj.system.service.ISysPostService;

 
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class SysPostController extends BaseController
{
    private final ISysPostService postService;

     
    @RequiresPermissions("system:post:list")
    @GetMapping("/list")
    public TableDataInfo list(SysPost post)
    {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "Post Management", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post)
    {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "Post Data");
    }

     
    @RequiresPermissions("system:post:query")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable String postId)
    {
        return AjaxResult.success(postService.selectPostById(postId));
    }

     
    @RequiresPermissions("system:post:add")
    @Log(title = "Post Management", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post)
    {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post)))
        {
            return AjaxResult.error("Failed to add position'" + post.getPostName() + "'position name already exists");
        }
        else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post)))
        {
            return AjaxResult.error("Failed to add position'" + post.getPostName() + "'Position code already exists");
        }
        post.setCreateBy(SecurityUtils.getUsername());
        return toAjax(postService.insertPost(post));
    }

     
    @RequiresPermissions("system:post:edit")
    @Log(title = "Post Management", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPost post)
    {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post)))
        {
            return AjaxResult.error("Failed to modify position'" + post.getPostName() + "'position name already exists");
        }
        else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post)))
        {
            return AjaxResult.error("Failed to modify position'" + post.getPostName() + "'Position code already exists");
        }
        post.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(postService.updatePost(post));
    }

     
    @RequiresPermissions("system:post:remove")
    @Log(title = "Post Management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable String[] postIds)
    {
        return toAjax(postService.deletePostByIds(postIds));
    }

     
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<SysPost> posts = postService.selectPostAll();
        return AjaxResult.success(posts);
    }
}
