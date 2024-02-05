package com.hmzhkj.system.controller;

import java.util.Arrays;

import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.file.service.ISysFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.utils.file.FileTypeUtils;
import com.hmzhkj.common.core.utils.file.MimeTypeUtils;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.auth.service.TokenService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.model.LoginUser;
import com.hmzhkj.system.service.ISysUserService;

 
@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class SysProfileController extends BaseController
{
    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private RedisService redisService;

     
    @GetMapping
    public AjaxResult profile()
    {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(username));
        ajax.put("postGroup", userService.selectUserPostGroup(username));
        return ajax;
    }

     
    @Log(title = "Personal Information", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("User modification failed'" + user.getUserName() + "'phone number already exists");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("User modification failed'" + user.getUserName() + "'Email account already exists");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        user.setAvatar(null);
        user.setDeptId(null);
        if (userService.updateUserProfile(user) > 0)
        {
                         loginUser.getSysUser().setNickName(user.getNickName());
            loginUser.getSysUser().setPhonenumber(user.getPhonenumber());
            loginUser.getSysUser().setEmail(user.getEmail());
            loginUser.getSysUser().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            if (StringUtils.isNotEmpty(user.getPhonenumber())){
                redisService.deleteObject(CacheConstants.PREFIX_SMS_MOBILE+user.getPhonenumber()+":2");
                redisService.deleteObject(CacheConstants.PREFIX_SMS_COUNT+user.getPhonenumber()+":2");
            }
            return AjaxResult.success();
        }
        return AjaxResult.error("Abnormal modification of personal information, please contact the administrator");
    }

     
    @Log(title = "Personal Information", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword)
    {
        oldPassword = userService.decryptPwd(oldPassword);
        newPassword = userService.decryptPwd(newPassword);
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            return AjaxResult.error("Password change failed, old password incorrect");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            return AjaxResult.error("The new password cannot be the same as the old password");
        }
        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
                         LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.getSysUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("Password modification exception, please contact the administrator");
    }
    
     
    @Log(title = "User profile picture", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty())
        {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            String extension = FileTypeUtils.getExtension(file);
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION))
            {
                return AjaxResult.error("The file format is incorrect, please upload" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "format");
            }
            String url = sysFileService.uploadFile(file);
            if (userService.updateUserAvatar(loginUser.getUsername(), url))
            {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", url);
                                 loginUser.getSysUser().setAvatar(url);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error("Image upload exception, please contact the administrator");
    }
}
