package com.hmzhkj.auth.service;


import com.hmzhkj.auth.form.SmsConfig;
import com.hmzhkj.auth.form.SmsLoginModel;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


 
@Slf4j
@Service
public class SmsUtil {
    @Autowired
    private SmsConfig smsConfig;

    public SendSmsResponse sendSms(SmsLoginModel item){
        try{
                                      Credential cred = new Credential(smsConfig.getSecretId(), smsConfig.getSecretKey());
                         HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
                         ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
                         SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
                         SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86"+item.getMobile()};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppId(smsConfig.getSmsSdkAppId());
            req.setSignName(smsConfig.getSignName());
            req.setTemplateId(smsConfig.getTemplateId());

            String[] templateParamSet1 = {item.getCode(), item.getMinute()};
            req.setTemplateParamSet(templateParamSet1);

                         SendSmsResponse resp = client.SendSms(req);
                         log.info(SendSmsResponse.toJsonString(resp));
            return resp;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
