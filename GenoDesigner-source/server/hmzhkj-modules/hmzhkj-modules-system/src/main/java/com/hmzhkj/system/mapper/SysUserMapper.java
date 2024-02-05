package com.hmzhkj.system.mapper;

import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.domain.dto.QueryParamDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysUserMapper
{

    List<SysUser> selectUserList(SysUser sysUser);







    List<SysUser> selectAllocatedList(SysUser user);
    int updateLastProgrammeId(@Param("lastProgrammeId") String lastProgrammeId, @Param("userId") String userId);

    List<SysUser> selectUnallocatedList(SysUser user);

    SysUser selectUserByUserName(String userName);

    SysUser selectUserByUserNameOrEmail(@Param("userName") String userName,
                                        @Param("userId") String userId,
                                        @Param("email") String email,
                                        @Param("phonenumber") String phone);

    SysUser selectUserByEmail(String email);

    SysUser selectUserByPhonenumber(String phonenumber);

    SysUser selectUserById(String userId);


    int insertUser(SysUser user);


    int updateUser(SysUser user);


    int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);


    int resetUserPwd(@Param("userName") String userName, @Param("password") String password);


    int deleteUserById(String userId);


    int deleteUserByIds(String[] userIds);


    int checkUserNameUnique(String userName);


    SysUser checkPhoneUnique(String phonenumber);


    SysUser checkEmailUnique(String email);



    List<Map<String, String>> selectUserListNotInProject(@Param("ids") String[] ids);


    List<Map<String, String>> selectUserListInProject(@Param("ids") String[] ids);

    List<Map<String, String>> selectUserAll();

    List<Map<String, String>> listExaminer(@Param("roles") String[] roles, @Param("userId") String userId);

    int auditUser(@Param("type") Integer type, @Param("userId") String userId);

    List<SysUser> selectAuditUserList(QueryParamDto dto);
}
