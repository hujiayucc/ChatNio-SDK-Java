package com.hujiayucc.chatnio.exception;

/**
 * Created by HuJiayu on 2024/1/7.
 * Unauthorized
 * 认证失败
 */
public class AuthException extends Exception {
    /**
     * 验证失败错误
     * @param message 服务器返回内容
     */
    public AuthException(String message) {
        super(message);
    }
}
