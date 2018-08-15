package com.gdut.CDD.exception;

/**
 * @author dengyq on 2018/8/11 上午11:29
 */
public class BaseException extends RuntimeException {
    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }
}
