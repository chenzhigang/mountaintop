package com.czg.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 返回码枚举类
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ResultCodeEnum {
    SUCCESS("200", "请求成功", "success"),
    SERVER_ERROR("500", "系统异常", "system error"),
    BUSINESS_ERROR("6000", "业务异常", "business error"),
    VALID_PARAM_ERROR("7000", "参数校验失败", "param valid error"),
    ;

    /**
     * 返回码
     */
    private String code;

    /**
     * 中文返回描述
     */
    private String zhMessage;

    /**
     * 英文返回描述
     */
    private String enMessage;

}
