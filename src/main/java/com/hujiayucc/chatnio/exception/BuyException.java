package com.hujiayucc.chatnio.exception;

/**
 * 购买失败
 */
public class BuyException extends Exception {
    /**
     * 购买失败错误
     * @param message 失败原因
     */
    public BuyException(String message) {
        super(message);
    }
}
