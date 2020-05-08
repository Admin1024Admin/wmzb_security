package com.l024.wmzbsecuritycore.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
//作用在方法或者注解上
@Target({ElementType.METHOD,ElementType.FIELD})
//运行时注解
@Retention(RetentionPolicy.RUNTIME)
//执行逻辑
@Constraint(validatedBy = MyConstrainValidator.class)
public @interface MyValidator {

    String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
