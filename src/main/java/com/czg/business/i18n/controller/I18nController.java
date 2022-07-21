package com.czg.business.i18n.controller;

import com.czg.utils.I18nUtil;
import com.czg.common.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/v1/i18n")
@RestController
@RequiredArgsConstructor
public class I18nController {

    public final HttpServletRequest request;

    @GetMapping("/message")
    public Result<Object> getMessage(@RequestParam(value = "code") String code) {
        return Result.success(I18nUtil.getMessage(code));
    }

}
