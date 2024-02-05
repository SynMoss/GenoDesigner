package com.hmzhkj.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hmzhkj.framework.feign.FeignAutoConfiguration;
import com.hmzhkj.framework.config.ApplicationConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
 @EnableAspectJAutoProxy(exposeProxy = true)
 @MapperScan("com.hmzhkj.**.mapper")
 @EnableAsync
 @Import({ ApplicationConfig.class, FeignAutoConfiguration.class })
public @interface EnableCustomConfig
{

}
