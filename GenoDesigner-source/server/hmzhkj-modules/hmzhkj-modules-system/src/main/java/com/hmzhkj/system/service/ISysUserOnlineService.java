package com.hmzhkj.system.service;

import com.hmzhkj.system.domain.SysUserOnline;
import com.hmzhkj.system.model.LoginUser;


public interface ISysUserOnlineService
{

    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    public SysUserOnline selectOnlineByUserName(String userName, LoginUser user);


    public SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);


    public SysUserOnline loginUserToUserOnline(LoginUser user);
}
