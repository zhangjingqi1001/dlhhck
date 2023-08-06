package com.pn.exception;

/**
 * 用户操作不当导致的异常
 */
public class BusinessException extends RuntimeException{

//  创建异常对象
    public BusinessException() {
//      访问父类构造器
        super();
    }

//  创建异常对象并同时指定异常信息
    public BusinessException(String message) {
//      访问父类构造器
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
