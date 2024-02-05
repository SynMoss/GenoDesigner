package com.hmzhkj.auth.service;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.hmzhkj.auth.form.CodeBody;
import com.hmzhkj.auth.form.RegisterBody;
import com.hmzhkj.auth.form.ResetBody;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.constant.Constants;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.enums.RegisterTypeEnum;
import com.hmzhkj.common.core.enums.UserStatus;
import com.hmzhkj.common.core.exception.CaptchaException;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.utils.uuid.IdUtils;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.model.LoginUser;
import com.hmzhkj.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class SysLoginService
{
    private final ISysUserService userService;

    private final SysPasswordService passwordService;

    private final SysRecordLogService recordLogService;
    private final MailTool mailTool;
    @Autowired
    RedisService redisService;
    @Value("${pwd.public}")
    private String pwdPublic;
    @Value("${pwd.private}")
    private String pwdPrivate;
    private final RedissonClient redissonClient;
    private String decryptPwd(String password){
        RSA rsa = new RSA(pwdPrivate,null);
        try {
            password = StrUtil.str(rsa.decrypt(password, KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
        }catch(Exception e){
            throw new ServiceException("Password decryption error");
        }
        return password;
    }

    public LoginUser login(String username, String password,long startTime)
    {

        if (StringUtils.isAnyBlank(username, password))
        {
            recordLogService.recordLogininfor("-",username, Constants.LOGIN_FAIL, "User/password must be filled in", System.currentTimeMillis()-startTime);
            throw new ServiceException("User/password must be filled in");
        }
        password = decryptPwd(password);


        LoginUser userInfo = userService.getInfoByUsername(username);
        if (userInfo==null)
        {
            recordLogService.recordLogininfor("-",username, Constants.LOGIN_FAIL, "Login user does not exist",System.currentTimeMillis()-startTime);
            throw new ServiceException("User not present/password incorrect");
        }

        SysUser user = userInfo.getSysUser();
        username = user.getUserName();
        String userId = user.getUserId();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            recordLogService.recordLogininfor(userId,username, Constants.LOGIN_FAIL, "Sorry, your account has been deleted",System.currentTimeMillis()-startTime);
            throw new ServiceException("Sorry, your account: "+username+" has been deleted");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            recordLogService.recordLogininfor(userId,username, Constants.LOGIN_FAIL, "The user has been deactivated. Please contact the administrator",System.currentTimeMillis()-startTime);
            throw new ServiceException("Sorry, your account: "+username+" has been deactivated");
        }
        if ("1".equals(user.getIsAudit()))
        {
            recordLogService.recordLogininfor(userId,username, Constants.LOGIN_FAIL, "Account review in progress",System.currentTimeMillis()-startTime);
            throw new ServiceException("your account:" + username + " Still under review, please be patient and wait");
        }
        passwordService.validate(user, password,startTime);
        recordLogService.recordLogininfor(userId,username, Constants.LOGIN_SUCCESS, "Login succeeded",System.currentTimeMillis()-startTime);
        return userInfo;
    }

    public LoginUser login2(String username, String password,long startTime) {

        if (StringUtils.isAnyBlank(username))
        {
            recordLogService.recordLogininfor("-",username, Constants.LOGIN_FAIL, "Email must be filled in", System.currentTimeMillis()-startTime);
            throw new ServiceException("Email must be filled in");
        }
        String pw = password;
        password = decryptPwd(password);


        LoginUser userInfo = userService.getInfoByUsername(username);
        if (userInfo==null)
        {

            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setEmail(username);
            sysUser.setRoleIds(new String[]{"1674688909045964800"});
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            R<Boolean> isSuccess = userService.registerUser(sysUser);
            Boolean result = isSuccess.getData();
            if(result !=null && result){
                return login2(username,pw,System.currentTimeMillis());
            }
        }

        SysUser user = userInfo.getSysUser();
        username = user.getUserName();
        String userId = user.getUserId();
        String roleId = user.getRoles().get(0).getRoleId();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            recordLogService.recordLogininfor(userId,username, Constants.LOGIN_FAIL, "Sorry, this email prohibits login",System.currentTimeMillis()-startTime);
            throw new ServiceException("Sorry, your email is "+username+" and login is prohibited");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            recordLogService.recordLogininfor(userId,username, Constants.LOGIN_FAIL, "The email has been deactivated. Please contact the administrator",System.currentTimeMillis()-startTime);
            throw new ServiceException("Sorry, the email: "+username+" has been deactivated");
        }
        if (!"1674688909045964800".equals(roleId))
        {
            recordLogService.recordLogininfor(userId,username, Constants.LOGIN_FAIL, "Please switch login method",System.currentTimeMillis()-startTime);
            throw new ServiceException("Please switch login method");
        }
        passwordService.validate(user, password,startTime);
        recordLogService.recordLogininfor(userId,username, Constants.LOGIN_SUCCESS, "Login succeeded",System.currentTimeMillis()-startTime);
        return userInfo;
    }
    public void logout(String userId,String loginName,long startTime)
    {
        recordLogService.recordLogininfor(userId,loginName, Constants.LOGOUT, "Exit successful",System.currentTimeMillis()-startTime);
    }



    public R<Boolean> register(RegisterBody registerBody,long startTime)
    {
        String username = registerBody.getUsername();
        String password = decryptPwd(registerBody.getPassword());

        String uuid = registerBody.getUuid();

        if (StringUtils.isAnyBlank(username, password))
        {
            throw new ServiceException("User/password must be filled in");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new ServiceException("The account length must be between 2 and 20 characters");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("The password length must be between 5 and 20 characters");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);



        sysUser.setLogonMode("1");


        sysUser.setRoleIds(new String[]{"1650412620610101248"});

        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        R<Boolean> isSuccess = userService.registerUser(sysUser);
        Boolean result = isSuccess.getData();
        if(result !=null && result){
            recordLogService.recordLogininfor("-",username, Constants.REGISTER, "login was successful",System.currentTimeMillis()-startTime);

        }
        return isSuccess;
    }

    public String sendCode(CodeBody codeBody) {
        String email=codeBody.getEmail();
        int type=codeBody.getType();
        Integer code = (int)(Math.random() * 900000)+ 100000;
        if(StringUtils.isEmpty(email)){
            throw new ServiceException("Email cannot be empty");
        }
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        Map<String,String> map = new HashedMap<>();
        map.put("email",email);
        map.put("code",code.toString());
        redisService.setCacheObject(verifyKey, map, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        mailTool.send(email,code,type);
        return uuid;
    }


    public void checkEmailCaptcha(String uuid,String code,String email) throws CaptchaException
    {

        if (StringUtils.isEmpty(code))
        {
            throw new CaptchaException("The verification code cannot be empty");
        }
        if (StringUtils.isEmpty(uuid))
        {
            throw new CaptchaException("The verification code has expired");
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        Map<String,String> map = null;
        try{
            map = redisService.getCacheObject(verifyKey);
        }catch (Exception e){
            System.err.println(e);
        }
        if(map == null){
            throw new CaptchaException("Verification code timeout");
        }else{

            if (!code.equalsIgnoreCase(map.get("code")))
            {
                throw new CaptchaException("Verification code error");
            }
            if (!email.equalsIgnoreCase(map.get("email")))
            {
                throw new CaptchaException("Email does not match verification code");
            }
        }
    }

    public void checkPhoneCaptcha( String smscode, String phone,String type) {

        if (StringUtils.isEmpty(smscode))
        {
            throw new CaptchaException("The verification code cannot be empty");
        }
        RLock lock = redissonClient.getLock(CacheConstants.REDIS_LOCK+phone);
        lock.lock();
        try {
            Object smsCountObj = redisService.getCacheObject(CacheConstants.PREFIX_SMS_COUNT+phone+":"+type);
            int smsCount=0;
            if(smsCountObj!=null){
                smsCount = (int)smsCountObj;
            }

            if(smsCount>4){
                throw new CaptchaException("The verification code has exceeded the maximum number of verifications, and the phone number has been locked");
            }
            Object code = redisService.getCacheObject(CacheConstants.PREFIX_SMS_MOBILE+phone+":"+type);
            if(code==null){
                throw new CaptchaException("Mobile verification code timeout");
            }
            if (!smscode.equals(code)) {
                smsCount++;
                redisService.incr(CacheConstants.PREFIX_SMS_COUNT+phone+":"+type,1);
                throw new CaptchaException("Mobile verification code error");
            }
        }finally {
            lock.unlock();
        }
    }
    public R<?> reset(ResetBody resetBody) {
        String userName = resetBody.getUsername();
        String password = resetBody.getPassword();

        String uuid = resetBody.getUuid();

        if (StringUtils.isAnyBlank(userName))
        {
            throw new ServiceException("User/password must be filled in");
        }
        if (StringUtils.isAnyBlank(password))
        {
            throw new ServiceException("User/password must be filled in");
        }


        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("The password length must be between 5 and 20 characters");
        }


        SysUser sysUser = new SysUser();

        sysUser.setUserName(userName);
        sysUser.setPassword(password);

        R<Boolean> isSuccess = userService.resetUser(sysUser);

        return isSuccess;
    }


}
