package com.l024.wmzbsecuritycore.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义注解执行逻辑
 */
public class MyConstrainValidator implements ConstraintValidator<MyValidator, Object> {

    /**
     * 初始化的时候执行
     * @param constraintAnnotation
     */
    @Override
    public void initialize(MyValidator constraintAnnotation) {
        System.out.println("注解初始化");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        return false;
    }
}
