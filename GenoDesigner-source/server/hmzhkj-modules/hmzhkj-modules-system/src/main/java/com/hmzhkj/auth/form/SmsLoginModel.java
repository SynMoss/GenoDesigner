package com.hmzhkj.auth.form;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SmsLoginModel {
    private String mobile;
    private String code;
    private String minute;
}
