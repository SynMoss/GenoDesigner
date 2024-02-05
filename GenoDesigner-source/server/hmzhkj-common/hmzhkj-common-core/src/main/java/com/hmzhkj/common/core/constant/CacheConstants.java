package com.hmzhkj.common.core.constant;

 
public class CacheConstants
{
     
    public final static long EXPIRATION = 4320;

     
    public final static long REFRESH_TIME = 2000;

     
    public final static int PASSWORD_MAX_RETRY_COUNT = 5;

     
    public final static long PASSWORD_LOCK_TIME = 10;

     
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

     
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

     
    public static final String SYS_CONFIG_KEY = "sys_config:";

     
    public static final String SYS_DICT_KEY = "sys_dict:";

     
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
    public static final String PREFIX_SMS_COUNT = "black_prefix_sms_count:";
    public static final String REDIS_LOCK = "lock:";
     
    public static final String PREFIX_SMS_MOBILE = "black_prefix_MOBILE:";
     
    public static final String PROCESS_CUT_SEQUENCE="processCutSequence:";
    public static final String PREFIX_FEATURE_LIST = "featureList:";
}
