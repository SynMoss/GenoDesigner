package com.hmzhkj.system.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.SpringUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.utils.bean.BeanValidators;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.annotation.DataScope;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysRole;
import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.domain.SysPost;
import com.hmzhkj.system.domain.SysUserPost;
import com.hmzhkj.system.domain.SysUserRole;
import com.hmzhkj.system.domain.dto.QueryParamDto;
import com.hmzhkj.system.mapper.*;
import com.hmzhkj.system.model.LoginUser;
import com.hmzhkj.system.service.ISysConfigService;
import com.hmzhkj.system.service.ISysPermissionService;
import com.hmzhkj.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private final SysUserMapper userMapper;

    
    private final SysRoleMapper roleMapper;

    
    private final SysPostMapper postMapper;

    
    private final SysUserRoleMapper userRoleMapper;

    
    private final SysUserPostMapper userPostMapper;

    
    private final ISysConfigService configService;
    private final ISysPermissionService permissionService;
    
    protected Validator validator;
    @Value("${pwd.private}")
    private String pwdPrivate;
    @Override
    public String decryptPwd(String password) {
        RSA rsa = new RSA(pwdPrivate,null);
        try {
            password = StrUtil.str(rsa.decrypt(password, KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
        }catch(Exception e){
            throw new ServiceException("Password decryption error");
        }
        return password;
    }

    @Override
    public LoginUser getInfoByUsername(String username) {
        SysUser sysUser = this.selectUserByLoginType(username);
        if (sysUser==null)
        {
            return null;
        }

        Set<String> roles = permissionService.getRolePermission(sysUser);

        Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return sysUserVo;
    }


    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user){
        String username = user.getUserName();
        String phoneNumber = user.getPhonenumber();
        String email = user.getEmail();
        if(!StringUtils.isEmpty(username)){
            user.setUserName(username.trim());
        }
        if(!StringUtils.isEmpty(phoneNumber)){
            user.setPhonenumber(phoneNumber.trim());
        }
        if(!StringUtils.isEmpty(email)){
            user.setEmail(email.trim());
        }
        return userMapper.selectUserList(user);
    }

    @Override
    public List selectUserListByIds(String[] ids) {
        List<SysUser> sysUsers = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SysUser sysUser = userMapper.selectUserById(ids[i]);
            sysUsers.add(sysUser);
        }
        return sysUsers;
    }



    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }


    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }


    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public SysUser selectUserByLoginType(String userName) {
        SysUser sysUser = userMapper.selectUserByUserName(userName);
        if(sysUser==null){
            sysUser = userMapper.selectUserByEmail(userName);
        }
        if(sysUser==null){
            sysUser = userMapper.selectUserByPhonenumber(userName);
        }
        return sysUser;
    }


    @Override
    public SysUser selectUserById(String userId)
    {
        return userMapper.selectUserById(userId);
    }


    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }


    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }


    @Override
    public String checkUserNameUnique(String userName)
    {

        int count = userMapper.checkUserNameUnique(userName);
        SysUser sysUser = userMapper.checkEmailUnique(userName);
        SysUser sysUser1 = userMapper.checkPhoneUnique(userName);
        if (count > 0||sysUser!=null||sysUser1!=null)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    @Override
    public String checkPhoneUnique(SysUser user)
    {
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(user.getUserId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    @Override
    public String checkEmailUnique(SysUser user)
    {
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(user.getUserId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("Not allowed to operate super administrator users");
        }
    }


    @Override
    public void checkUserDataScope(String userId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("No permission to access user data！");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user)
    {
        user.genId();

        int rows = userMapper.insertUser(user);

        insertUserPost(user);

        insertUserRole(user);
        return rows;
    }


    @Override
    @Transactional
    public R<Boolean> registerUser(SysUser user)
    {
        String username = user.getUserName();
        String email=user.getEmail();
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return R.fail("The current system has not enabled registration function！");
        }
        if (UserConstants.NOT_UNIQUE.equals(this.checkUserNameUnique(username)))
        {
            return R.fail("Failed to save user'" + username + "'registered account already exists");
        }
        if (this.checkUserEmailUnique(email))
        {
            return R.fail("email:'" + email + "'Registered, can log in directly");
        }
        user.genId();
        user.setIsAudit("0");
        boolean b = userMapper.insertUser(user) > 0;
        if(b){
            String userId = user.getUserId();
            String[] roleIds = user.getRoleIds();
            if(roleIds != null && roleIds.length > 0){
                insertUserRole(userId,roleIds);
            }
        }
        return R.ok(b);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user)
    {
        String userId = user.getUserId();

        userRoleMapper.deleteUserRoleByUserId(userId);

        insertUserRole(user);

        userPostMapper.deleteUserPostByUserId(userId);

        insertUserPost(user);
        return userMapper.updateUser(user);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(String userId, String[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }


    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }


    @Override
    public int updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user);
    }


    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }


    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }


    @Override
    public int resetUserPwd(String userName, String password)
    {
        return userMapper.resetUserPwd(userName, password);
    }


    public void insertUserRole(SysUser user)
    {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }


    public void insertUserPost(SysUser user)
    {
        String[] posts = user.getPostIds();
        if (StringUtils.isNotEmpty(posts))
        {

            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (String postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            userPostMapper.batchUserPost(list);
        }
    }


    public void insertUserRole(String userId, String[] roleIds)
    {
        if (StringUtils.isNotEmpty(roleIds))
        {

            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (String roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserById(String userId)
    {

        userRoleMapper.deleteUserRoleByUserId(userId);

        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(String[] userIds)
    {
        for (String userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }

        userRoleMapper.deleteUserRole(userIds);

        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }


    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("Importing user data cannot be empty！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {

                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、account number " + user.getUserName() + " Import was successful");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、account number " + user.getUserName() + " update success");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、account number " + user.getUserName() + " Already exists");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、account number " + user.getUserName() + " Import failed：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "Sorry, import failed!There are " + failureNum + "  data formats that are incorrect,and the errors are as follows:");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "Congratulations, all data has been successfully imported！There are " + successNum + " data formats that are incorrect，The data is as follows：");
        }
        return successMsg.toString();
    }


    @Override
    public List<Map<String, String>> selectUserListNotInProject(String[] ids) {
        return userMapper.selectUserListNotInProject(ids);
    }

    @Override
    public List<Map<String, String>> selectUserListInProject(String[] ids) {
        return userMapper.selectUserListInProject(ids);
    }

    @Override
    public List<Map<String, String>> selectUserAll() {
        return userMapper.selectUserAll();
    }

    @Override
    public List<Map<String, String>> listExaminer(String[] roles) {
        String userId = SecurityUtils.getUserId();
        return userMapper.listExaminer(roles,userId);
    }

    @Override
    public AjaxResult checkUserEmail(String userName, String email) {
        if(userName != null){
            SysUser sysUser = userMapper.selectUserByUserName(userName);
            if(sysUser!=null){
                return AjaxResult.success("The username already exists",true);
            }
        }
        return AjaxResult.success("The username can be used",false);


    }

    @Override
    public SysUser selectUserByName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public boolean checkUserEmailUnique(String email) {
        SysUser sysUser = userMapper.selectUserByEmail(email);
        if(sysUser==null){
            return false;
        }
        return true;
    }

    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhonenumber(phoneNumber);
    }

    @Override
    public int auditUser(Integer type, String userId) {
        return userMapper.auditUser(type,userId);
    }

    @Override
    public List<SysUser> selectAuditUserList(QueryParamDto dto) {
        return userMapper.selectAuditUserList(dto);
    }

    @Override
    public Boolean checkUsernameOrEmail(String userName, String email, String phone, String id) {
        if(userName != null){
            SysUser sysUser = userMapper.selectUserByUserNameOrEmail(userName, id, email, phone);
            if(sysUser!=null){
                return true;
            }
        }
        if(email != null){
            SysUser sysUser2 = userMapper.selectUserByUserNameOrEmail(userName, id, email, phone);
            if(sysUser2!=null){
                return true;
            }
        }
        if(phone != null){
            SysUser sysUser3 = userMapper.selectUserByUserNameOrEmail(userName, id, email, phone);
            if(sysUser3!=null){
                return true;
            }
        }
        return false;
    }
    @Override
    public R<Boolean> resetUser(SysUser sysUser) {
        SysUser sysUser1=userMapper.selectUserByUserName(sysUser.getUserName());

        if (sysUser1==null)
        {
            return R.fail("User reset failed, registered account does not exist");
        }
        sysUser1.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        return R.ok(this.resetPwd(sysUser1)>0);
    }
}
