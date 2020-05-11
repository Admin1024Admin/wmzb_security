package com.l024.wmzbsecuritycore.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码不匹配异常
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String detail) {
        super(detail);
    }
}
