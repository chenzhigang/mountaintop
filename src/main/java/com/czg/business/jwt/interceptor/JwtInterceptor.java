package com.czg.business.jwt.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.czg.business.jwt.utils.JwtUtil;
import com.czg.common.Result;
import com.czg.exception.BusinessException;
import com.czg.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tokenParamName = "token";
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            token = request.getHeader(tokenParamName);
        }
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(tokenParamName);
        }
        if (StringUtils.isBlank(token)) {
            throw new ServerException("校验失效，请登录再操作");
        }
        Result<Object> result = JwtUtil.validateJwt(token);
        if (!Result.isSuccess(result)) {
            throw new BusinessException("授权信息校验失败：" + result.getMessage());
        }
        return true;
    }

    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
