package com.hujiayucc.chatnio.exception;

public class BuyException extends Exception {
    public BuyException(String message) {
        super(message);
    }

    public BuyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuyException(Throwable cause) {
        super(cause);
    }
}
