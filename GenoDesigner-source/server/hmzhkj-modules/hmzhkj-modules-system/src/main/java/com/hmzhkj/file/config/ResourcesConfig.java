package com.hmzhkj.file.config;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ResourcesConfig implements WebMvcConfigurer
{

    @Value("${file.path}")
    private String localFilePath;


    @Value("${file.prefix}")
    public String localFilePrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {

        registry.addResourceHandler(localFilePrefix + "/**")
                .addResourceLocations("file:" + localFilePath + File.separator).setCacheControl(CacheControl.noCache()).setCachePeriod(0);
    }
    

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping(localFilePrefix  + "/**")

                .allowedOrigins("*")

                .allowedMethods("GET");
    }
}