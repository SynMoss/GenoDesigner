package com.hmzhkj.framework.config;

import com.hmzhkj.framework.interceptor.HeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{

    public static final String[] excludeUrls = { "/login", "/logout", "/refresh" ,"/auth/emailcode"};

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(getHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);
    }


    public HeaderInterceptor getHeaderInterceptor()
    {
        return new HeaderInterceptor();
    }
}
