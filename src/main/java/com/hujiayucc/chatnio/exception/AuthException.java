package com.hujiayucc.chatnio.exception;

/**
 * Created by HuJiayu on 2024/1/7.
 * Unauthorized
 * 认证失败
 */
public class AuthException extends Exception{
    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
}
