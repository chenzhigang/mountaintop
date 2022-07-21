package com.czg.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 参数校验枚举类
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ValidParamErrorEnum {
    PHONE_INCORRECT("7001", "手机号码格式不正确"),
    EMAIL_INCORRECT("7002", "邮箱格式不正确"),
    ID_CARD_INCORRECT("7003", "身份证号码不正确"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String msg;

}
