package com.hmzhkj.system.controller;

import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.utils.poi.ExcelUtil;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.InnerAuth;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.framework.auth.AuthUtil;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysDept;
import com.hmzhkj.system.domain.SysRole;
import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.model.LoginUser;
import com.hmzhkj.system.domain.dto.QueryParamDto;
import com.hmzhkj.system.domain.vo.SysUserVo;
import com.hmzhkj.system.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

 
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController extends BaseController
{
    private final ISysUserService userService;

    private final ISysRoleService roleService;

    private final ISysDeptService deptService;

    private final ISysPostService postService;

    private final ISysPermissionService permissionService;

    private final ISysConfigService configService;
     
    @Value("${file.prefix}")
    public String localFilePrefix;

     
    @Value("${file.domain}")
    public String domain;
     
    @RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        user.setIsAudit("0");
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "USER", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserVo ids)
    {
        List list = new ArrayList();
        list= userService.selectUserListByIds(ids.getIds());
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "user data");
    }

    @Log(title = "USER", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "user data");
    }

     
    @InnerAuth
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByLoginType(username);
        if (StringUtils.isNull(sysUser))
        {
            return R.fail("Incorrect username or password");
        }
                 Set<String> roles = permissionService.getRolePermission(sysUser);
                 Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }

     
    @GetMapping("/auditList")
    public TableDataInfo auditList(QueryParamDto dto)
    {
        startPage();
        List<SysUser> list = userService.selectAuditUserList(dto);
        return getDataTable(list);
    }

     
    @PutMapping("/audit")
    public AjaxResult auditUser(Integer type,String userId){

        return toAjax(userService.auditUser(type,userId));
    }

     
    @InnerAuth
    @GetMapping("/info/id/{id}")
    public R<SysUser> infoById(@PathVariable("id") String id)
    {
        SysUser sysUser = userService.selectUserById(id);
        return R.ok(sysUser);
    }

     
    @InnerAuth
    @PostMapping("/info/project/list")
    public R<List<Map<String, String>>> infoNotINProject(@RequestBody String[] ids)
    {
        List<Map<String, String>> users = userService.selectUserListNotInProject(ids);
        return R.ok(users);
    }

     
    @InnerAuth
    @PostMapping("/info/project/list2")
    public R<List<Map<String, String>>> infoINProject(@RequestBody String[] ids)
    {
        List<Map<String, String>> users = userService.selectUserListInProject(ids);
        return R.ok(users);
    }
     
    @InnerAuth
    @GetMapping("/list/all")
    public R<List<Map<String, String>>> allUser()
    {
        List<Map<String, String>> users = userService.selectUserAll();
        return R.ok(users);
    }

     
    @InnerAuth
    @PostMapping("/list/examiner")
    public R<List<Map<String, String>>> listExaminer(@RequestBody String[] roles)
    {
        List<Map<String, String>> users = userService.listExaminer(roles);
        return R.ok(users);
    }

     
    @InnerAuth
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody SysUser sysUser)
    {
        return userService.registerUser(sysUser);
    }
     
    @InnerAuth
    @PostMapping("/reset")
    public R<Boolean> reset(@RequestBody SysUser sysUser)
    {
        SysUser sysUser1;
        if(StringUtils.isNotEmpty(sysUser.getPhonenumber())){
            String phoneNumber = sysUser.getPhonenumber();
            sysUser1 = userService.selectUserByPhoneNumber(phoneNumber);
        }else{
            String email = sysUser.getEmail();
            sysUser1 = userService.selectUserByEmail(email);
        }
        if (sysUser1==null)
        {
            return R.fail("User reset failed, registered account does not exist");
        }
        sysUser1.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        sysUser1.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(userService.resetPwd(sysUser1)>0);
    }
     
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = userService.selectUserById(SecurityUtils.getUserId());
                 Set<String> roles = permissionService.getRolePermission(user);
                 Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("menuLeftImg", domain+localFilePrefix+"/img/menu-left.png");
        return ajax;
    }

     
    @RequiresPermissions("system:user:query")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) String userId)
    {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
                 ajax.put("roles", roles);
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            SysUser sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

     
    @RequiresPermissions("system:user:add")
    @Log(title = "USER", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("User add failed'" + user.getUserName() + "'Login account already exists");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("User add failed'" + user.getUserName() + "'Mobile number already exists");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("User add failed'" + user.getUserName() + "'Email account already exists");
        }
        String password = userService.decryptPwd(user.getPassword());
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(password));
        return toAjax(userService.insertUser(user));
    }

     
    @RequiresPermissions("system:user:edit")
    @Log(title = "USER", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("User modification failed'" + user.getUserName() + "'phone number already exists");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("User modification failed'" + user.getUserName() + "'email account already exists");
        }
        user.setUpdateBy(SecurityUtils.getUsername());
        userService.updateUser(user);
        AuthUtil.logoutByUserIds(new String[]{user.getUserId()});
        return AjaxResult.success();
    }

     
    @RequiresPermissions("system:user:remove")
    @Log(title = "USER", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        if (ArrayUtils.contains(userIds, SecurityUtils.getUserId())||ArrayUtils.contains(userIds,"1"))
        {
            return AjaxResult.error("The current user cannot be deleted");
        }
        userService.deleteUserByIds(userIds);
        AuthUtil.logoutByUserIds(userIds);
        return AjaxResult.success();
    }

     
    @RequiresPermissions("system:user:edit")
    @Log(title = "USER", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        String password = userService.decryptPwd(user.getPassword());
        user.setPassword(SecurityUtils.encryptPassword(password));
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

     
    @RequiresPermissions("system:user:edit")
    @Log(title = "USER", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(SecurityUtils.getUsername());
        userService.updateUserStatus(user);
        AuthUtil.logoutByUserIds(new String[]{user.getUserId()});
        return AjaxResult.success();
    }

     
    @RequiresPermissions("system:user:query")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") String userId)
    {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

     
    @RequiresPermissions("system:user:edit")
    @Log(title = "USER", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(String userId, String[] roleIds)
    {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        AuthUtil.logoutByUserIds(new String[]{userId});
        return success();
    }

     
    @RequiresPermissions("system:user:list")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept)
    {
        return AjaxResult.success(deptService.selectUserDeptTreeList(dept));
    }

     
    @GetMapping("/checkUserEmail")
    public AjaxResult checkUserEmail( String userName, String email)
    {
        return userService.checkUserEmail(userName,email);
    }

     
    @GetMapping("/checkUsernameOrEmail")
    public AjaxResult checkUsernameOrEmail( String userName, String email, String phone, String id)
    {
        return AjaxResult.success(userService.checkUsernameOrEmail(userName, email, phone, id));
    }
}
