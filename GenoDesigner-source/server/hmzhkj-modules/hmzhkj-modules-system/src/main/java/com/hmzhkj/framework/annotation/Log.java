package com.hmzhkj.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.enums.OperatorType;

 
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
     
    public String title() default "";

     
    public BusinessType businessType() default BusinessType.OTHER;

     
    public OperatorType operatorType() default OperatorType.MANAGE;

     
    public boolean isSaveRequestData() default true;

     
    public boolean isSaveResponseData() default true;
}
