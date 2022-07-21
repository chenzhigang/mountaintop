package com.czg.exception;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.czg.common.Result;
import com.czg.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局异常处理器
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<Object> exceptionHandler(Exception e) {
        String msg = e.getMessage();
        msg = StringUtils.isBlank(msg) ? ResultCodeEnum.SERVER_ERROR.getZhMessage() : msg;
        log.error(msg, e);
        if (e instanceof BusinessException) {
            return Result.failed(((BusinessException) e).getCode(), msg);
        }
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errorList = bindException.getAllErrors();
            msg = errorList.get(0).getDefaultMessage();
            return Result.failed(ResultCodeEnum.VALID_PARAM_ERROR.getCode(), msg);
        }
        return Result.failed(msg);
    }

}
