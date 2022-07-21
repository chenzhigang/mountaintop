package com.czg.business.user.controller;

import com.czg.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @GetMapping(value = "/info")
    public Result<Object> getUserInfo() {
        return Result.success();
    }

    @PostMapping(value = "/add")
    public Result<Object> addUser() {
        return Result.success();
    }

}
