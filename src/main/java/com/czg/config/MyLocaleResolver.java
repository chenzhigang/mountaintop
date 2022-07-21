package com.czg.config;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.czg.common.LanguageEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化配置
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class MyLocaleResolver implements LocaleResolver {

    private final HttpServletRequest request;

    public Locale getLocale() {
        return resolveLocale(request);
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        if (StringUtils.isBlank(language)) {
            language = request.getHeader("lang");
        }
        if (StringUtils.isBlank(language)) {
            language = request.getParameter("lang");
        }
        log.info("language:{}", language);
        Locale locale;
        if (StringUtils.isNotBlank(language)) {
            try {
                if (!LanguageEnum.getList().contains(language)) {
                    log.info("unSupport Language:{}", language);
                    return Locale.CHINESE;
                }
                locale = new Locale(language);
            } catch (Exception e) {
                log.warn("resolver language error:{}", e.getMessage(), e);
                locale = Locale.CHINESE;
            }
        } else {
            locale = Locale.CHINESE;
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // Do nothing
    }
}
