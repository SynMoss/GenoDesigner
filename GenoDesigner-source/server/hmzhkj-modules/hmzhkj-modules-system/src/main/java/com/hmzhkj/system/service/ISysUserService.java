package com.hmzhkj.system.service;

import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.domain.dto.QueryParamDto;
import com.hmzhkj.system.model.LoginUser;

import java.util.List;
import java.util.Map;


public interface ISysUserService
{

    String decryptPwd(String password);
    LoginUser getInfoByUsername(String username);

    public List<SysUser> selectUserList(SysUser user);


    public List selectUserListByIds(String[] ids);


    public List<SysUser> selectAllocatedList(SysUser user);


    public List<SysUser> selectUnallocatedList(SysUser user);


    SysUser selectUserByUserName(String userName);

    SysUser selectUserByLoginType(String userName);

    public SysUser selectUserById(String userId);


    public String selectUserRoleGroup(String userName);


    public String selectUserPostGroup(String userName);

    public String checkUserNameUnique(String userName);


    public String checkPhoneUnique(SysUser user);


    public String checkEmailUnique(SysUser user);


    public void checkUserAllowed(SysUser user);


    public void checkUserDataScope(String userId);


    public int insertUser(SysUser user);


    R<Boolean> registerUser(SysUser user);


    public int updateUser(SysUser user);


    public void insertUserAuth(String userId, String[] roleIds);


    public int updateUserStatus(SysUser user);



    public int updateUserProfile(SysUser user);


    public boolean updateUserAvatar(String userName, String avatar);


    public int resetPwd(SysUser user);


    public int resetUserPwd(String userName, String password);


    public int deleteUserById(String userId);


    public int deleteUserByIds(String[] userIds);


    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);


    List<Map<String, String>> selectUserListNotInProject(String[] ids);


    List<Map<String, String>> selectUserListInProject(String[] ids);


    List<Map<String, String>> selectUserAll();


    List<Map<String, String>> listExaminer(String[] roles);


    AjaxResult checkUserEmail(String userName, String email);


    SysUser selectUserByName(String userName);


    boolean checkUserEmailUnique(String email);


    SysUser selectUserByEmail(String email);

    SysUser selectUserByPhoneNumber(String phoneNumber);

    int auditUser(Integer type, String userId);

    List<SysUser> selectAuditUserList(QueryParamDto dto);

    Boolean checkUsernameOrEmail(String userName, String email, String phone,  String id);
    R<Boolean> resetUser(SysUser sysUser);
}
