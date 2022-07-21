package com.czg.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 国际化支持的语言枚举类
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum LanguageEnum {
    CHINESE("zh_CN"),
    ENGLISH("en_US"),
    ;

    private String messageCode;

    private static final List<String> list = new ArrayList<>();

    static {
        for (LanguageEnum l : values()) {
            list.add(l.getMessageCode());
        }
    }

    public static List<String> getList() {
        return list;
    }
}
