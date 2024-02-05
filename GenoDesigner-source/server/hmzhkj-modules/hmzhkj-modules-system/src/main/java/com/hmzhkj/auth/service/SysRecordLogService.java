package com.hmzhkj.auth.service;

import com.hmzhkj.common.core.constant.Constants;
import com.hmzhkj.common.core.constant.SecurityConstants;
import com.hmzhkj.common.core.utils.ServletUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.utils.ip.IpUtils;
import com.hmzhkj.system.domain.SysLogininfor;
import com.hmzhkj.system.service.ISysLogininforService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 
@Component
@RequiredArgsConstructor
public class SysRecordLogService
{
    private final ISysLogininforService logininforService;
     
    public void recordLogininfor(String userId,String username, String status, String message,long consumeTime)
    {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserId(userId);
        logininfor.setUserName(username);
        logininfor.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMsg(message);
        logininfor.setConsumeTime(consumeTime);
                 if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER))
        {
            logininfor.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        }
        else if (Constants.LOGIN_FAIL.equals(status))
        {
            logininfor.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        logininforService.insertLogininfor(logininfor);
    }
}
