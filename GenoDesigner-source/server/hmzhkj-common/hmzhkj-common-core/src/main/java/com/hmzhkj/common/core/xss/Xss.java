package com.hmzhkj.common.core.xss;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Constraint(validatedBy = { XssValidator.class })
public @interface Xss
{
    String message()

    default "Do not allow any scripts to run";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
