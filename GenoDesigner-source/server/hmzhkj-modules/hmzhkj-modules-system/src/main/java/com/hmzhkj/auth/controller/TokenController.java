package com.hmzhkj.auth.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.hmzhkj.auth.form.*;
import com.hmzhkj.auth.service.SmsUtil;
import com.hmzhkj.auth.service.SysLoginService;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.JwtUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.auth.AuthUtil;
import com.hmzhkj.auth.service.TokenService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.model.LoginUser;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

 
@RestController
@Slf4j
@RequiredArgsConstructor
public class TokenController
{
    private final TokenService tokenService;

    private final SysLoginService sysLoginService;
    private final SmsUtil smsUtil;
    private final RedisService redisService;
    private final RedissonClient redissonClient;

     
    @GetMapping(value = "/sms")
    public R<?> sms(String mobile,String type) {
                 if(StringUtils.isEmpty(mobile)){
            throw new ServiceException("Mobile phone number cannot be empty!");
        }
        RLock lock = redissonClient.getLock(CacheConstants.REDIS_LOCK+mobile);
        lock.lock();
        try {
            Object smsCountObj = redisService.getCacheObject(CacheConstants.PREFIX_SMS_COUNT+mobile+":"+type);
            int smsCount=0;
            if(smsCountObj!=null){
                smsCount = (int)smsCountObj;
            }
                         if(smsCount>4){
                throw new ServiceException("The verification code has exceeded the maximum number of verifications, and the phone number has been locked");
            }
            Object object = redisService.getCacheObject(CacheConstants.PREFIX_SMS_MOBILE+mobile+":"+type);
            if (object != null) {
                throw new ServiceException("The verification code is still valid within 10 minutes！");
            }

                         String captcha = RandomUtil.randomNumbers(6);

            JSONObject obj = new JSONObject();
            obj.put("code", captcha);
            try {
                boolean b = false;
                SendSmsResponse res = smsUtil.sendSms(new SmsLoginModel().setMobile(mobile).setCode(captcha).setMinute("10"));
                if(res!=null&&"Ok".equals(res.getSendStatusSet()[0].getCode())){
                    b=true;
                }else{
                    b= false;
                }
                if (b == false) {
                    throw new ServiceException("SMS verification code sending failed, please try again later");
                }
                                 redisService.setCacheObject(CacheConstants.PREFIX_SMS_MOBILE+mobile+":"+type, captcha, 10l, TimeUnit.MINUTES);
                                 redisService.setCacheObject(CacheConstants.PREFIX_SMS_COUNT+mobile+":"+type,0,10l,TimeUnit.HOURS);
                                                               } catch (Exception e) {
                log.error(e.getMessage());
                throw new ServiceException("SMS verification code sending failed, please try again later");
            }
        } finally {
            lock.unlock();
        }
        return R.ok("Successfully sent");
    }
    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
                 LoginUser userInfo = sysLoginService.login(form.getUsername().trim(), form.getPassword().trim(), System.currentTimeMillis());
                 return R.ok(tokenService.createToken(userInfo));
    }
    @PostMapping("login2")
    public R<?> login2(String email,String password)
    {
                           LoginUser userInfo = sysLoginService.login2(email.trim(), password,System.currentTimeMillis());
                 return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        long startTime= System.currentTimeMillis();
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            String userId = JwtUtils.getUserId(token);
                         AuthUtil.logoutByToken(token);
                         sysLoginService.logout(userId,username,startTime);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
                         tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        long startTime= System.currentTimeMillis();
                 System.out.println(registerBody);
        return sysLoginService.register(registerBody,startTime);
    }

    @PostMapping("emailcode")
    public R<?> getEmailCode(@RequestBody CodeBody codeBody){
        return R.ok(sysLoginService.sendCode(codeBody));
    }

    @PostMapping("reset")
    public R<?> reset(@RequestBody ResetBody resetBody)
    {
                 return sysLoginService.reset(resetBody);
    }
    @PostMapping("checkEmailCaptcha")
    public AjaxResult checkEmailCaptcha(@RequestBody RegisterBody registerBody)
    {
        sysLoginService.checkEmailCaptcha(registerBody.getUuid(),registerBody.getCode(),registerBody.getEmail());
        return AjaxResult.success();
    }

    @PostMapping("checkPhoneCaptcha")
    public AjaxResult checkPhoneCaptcha(@RequestBody RegisterBody registerBody)
    {
        sysLoginService.checkPhoneCaptcha(registerBody.getCode(),registerBody.getPhone(),"2");
        return AjaxResult.success();
    }

}
