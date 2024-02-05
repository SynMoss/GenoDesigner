package com.hmzhkj.auth.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties( prefix= "sms")
public class SmsConfig {
    private String secretId;
    private String secretKey;
    private String smsSdkAppId;
    private String signName;
    private String templateId;
}
