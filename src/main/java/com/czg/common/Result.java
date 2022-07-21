package com.czg.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回类
 *
 * @param <T> 泛型
 */
@SuppressWarnings("unused")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    /**
     * 返回码（具体见枚举类）
     */
    private String code;

    /**
     * 返回描述信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public static <T> Result<T> result(ResultCodeEnum codeEnum) {
        return new Result<>(codeEnum.getCode(), codeEnum.getZhMessage(), null);
    }

    public static <T> Result<T> success(String code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(String code, String message) {
        return success(code, message, null);
    }

    public static <T> Result<T> failed(String code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> failed(String message) {
        return new Result<>(ResultCodeEnum.SERVER_ERROR.getCode(), message, null);
    }

    public static <T> Result<T> failed(String code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> success(T data) {
        return success(ResultCodeEnum.SUCCESS.getCode(), "", data);
    }

    public static <T> Result<T> failed(T data) {
        return failed("", "", data);
    }

    public static boolean isSuccess(Result<?> result) {
        if (null == result) {
            return false;
        }
        return ResultCodeEnum.SUCCESS.getCode().equals(result.getCode());
    }
}
