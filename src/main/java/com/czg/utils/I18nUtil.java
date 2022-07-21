package com.czg.utils;

import com.czg.config.MyLocaleResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

/**
 * 国际化工具类
 */
@RefreshScope
@Slf4j
@Component
public class I18nUtil {

    @Value("${spring.messages.basename}")
    private String baseName;

    private final MyLocaleResolver resolver;

    private static MyLocaleResolver customLocaleResolver;

    private static String path;

    public I18nUtil(MyLocaleResolver resolver) {
        this.resolver = resolver;
    }

    @PostConstruct
    public void init() {
        setBaseName(baseName);
        setCustomLocaleResolver(resolver);
    }

    public static String getMessage(String code) {
        Locale locale = customLocaleResolver.getLocale();
        return getMessage(code, null, code, locale);
    }

    public static String getStationLetterMessage(String code, String lang) {
        Locale locale = Objects.equals(lang, "en") ? Locale.US : Locale.SIMPLIFIED_CHINESE;
        return getMessage(code, null, code, locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        messageSource.setBasename(path);
        String content;
        try {
            content = messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            log.error("国际化参数获取失败：{}", e.getMessage(), e);
            content = defaultMessage;
        }
        return content;
    }

    public static void setBaseName(String baseName) {
        I18nUtil.path = baseName;
    }

    public static void setCustomLocaleResolver(MyLocaleResolver customLocaleResolver) {
        I18nUtil.customLocaleResolver = customLocaleResolver;
    }
}
