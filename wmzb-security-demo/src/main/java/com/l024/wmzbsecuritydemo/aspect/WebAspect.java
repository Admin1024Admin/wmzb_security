package com.l024.wmzbsecuritydemo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 切片
 */
@Aspect
@Component
public class WebAspect {
    //around是条件，给哪些方法加上
    @Around("execution(* com.l024.wmzbsecuritydemo.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入切点");
        Object proceed = pjp.proceed();
        System.out.println("切点结束");
        return proceed;
    }
}
