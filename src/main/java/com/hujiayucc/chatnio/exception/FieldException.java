package com.hujiayucc.chatnio.exception;

/**
 * 字段错误
 */
public class FieldException extends Exception {
    /**
     * 字段错误
     * @param message 错误信息
     */
    public FieldException(String message) {
        super(message);
    }

    /**
     * 字段错误
     * @param message 错误信息
     * @param cause 抛出异常
     */
    public FieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
