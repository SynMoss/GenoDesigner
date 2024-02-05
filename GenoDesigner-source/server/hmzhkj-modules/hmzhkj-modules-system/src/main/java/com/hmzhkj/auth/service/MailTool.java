package com.hmzhkj.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailTool {
    private static final String REGISTER="register";
    private static final String FORGET_PASSWORD="forgot password";
    private static final String BIND_EMAIL="Bind email";
    @Value("${mail.content}")
    private String mailContent;


    public boolean send(String emailaddress,Integer message,int type) {

        String s="";
        if(type==0){
            s=REGISTER;
        }else if(type==1){
            s=FORGET_PASSWORD;
        }else {
            s=BIND_EMAIL;
        }

        String msg = String.format(mailContent,s,message);

        HtmlEmail email = new HtmlEmail();
        try {

            email.setHostName("smtp.qq.com");

            email.setCharset("UTF-8");

            email.addTo(emailaddress);

            email.setFrom("1151090654@qq.com", "Chromosome tools");

            email.setAuthentication("1151090654@qq.com", "cvxpzwpouyprhaci");

            email.setSubject("Email verification code");

            email.setMsg(msg);

            email.send();
            return true;
        }catch (EmailException e){
            log.error(e.getMessage());
            return false;
        }
    }
}
