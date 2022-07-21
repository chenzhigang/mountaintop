package com.czg.business.jwt.controller;

import com.czg.business.jwt.utils.JwtUtil;
import com.czg.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/token")
@Slf4j
@RefreshScope
@RestController
public class JwtController {

    @Value("${jwt.token.validTimes:3600L}")
    private long jwtTokenValidTimes;

    @PostMapping(value = "/login")
    public Result<Object> login() {
        String token = JwtUtil.createJwt(String.valueOf(new Date()), "user", jwtTokenValidTimes * 1000);
        return Result.success(token);
    }

}
