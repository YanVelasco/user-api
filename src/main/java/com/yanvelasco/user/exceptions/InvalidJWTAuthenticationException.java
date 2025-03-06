package com.yanvelasco.user.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJWTAuthenticationException extends AuthenticationException {
    public InvalidJWTAuthenticationException(String message) {
        super(message);
    }
}
