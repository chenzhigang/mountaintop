package com.czg.exception;

import com.czg.common.ResultCodeEnum;
import com.czg.common.ValidParamErrorEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义业务异常类
 */
@SuppressWarnings("unused")
@Slf4j
public class ServerException extends RuntimeException {

    /**
     * 错误码
     */
    @Getter
    private final String code;

    public ServerException(ValidParamErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

    public ServerException(String message) {
        super(message);
        this.code = ResultCodeEnum.SERVER_ERROR.getCode();
    }

    public ServerException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCodeEnum.SERVER_ERROR.getCode();
    }

    public ServerException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public ServerException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public ServerException(Throwable cause) {
        super(cause);
        this.code = ResultCodeEnum.SERVER_ERROR.getCode();
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = ResultCodeEnum.SERVER_ERROR.getCode();
    }
}
