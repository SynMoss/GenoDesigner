package com.hmzhkj;

import com.hmzhkj.framework.annotation.EnableCustomConfig;
import com.hmzhkj.framework.annotation.EnableRyFeignClients;
import com.hmzhkj.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class HmzhkjSystemApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(com.hmzhkj.HmzhkjSystemApplication.class, args);
        System.out.println("System started\n");
    }
}
