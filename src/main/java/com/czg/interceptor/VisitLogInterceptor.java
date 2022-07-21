package com.czg.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.czg.business.log.model.VisitLog;
import com.czg.business.log.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 访问日志记录拦截器
 */
@DependsOn("springUtil")
@Slf4j
@RequiredArgsConstructor
@Component
public class VisitLogInterceptor implements HandlerInterceptor {

    private final VisitLogService visitLogService = SpringUtil.getBean("visitLogService");

    private final MyResponseBodyStorage myResponseBodyStorage;

    private VisitLog visitLog;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 组装请求相关参数
        visitLog = new VisitLog();
        // 请求地址
        visitLog.setRequestUri(request.getRequestURI());
        visitLog.setRequestMethod(request.getMethod());
        visitLog.setBody(new String(new ContentCachingRequestWrapper(request).getBody(), StandardCharsets.UTF_8));
        // 请求头
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, Object> headerMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            headerMap.put(key, request.getHeader(key));
        }
        visitLog.setHeader(JSON.toJSONString(headerMap));
        // 请求参数
        Map<String, String[]> map = request.getParameterMap();
        visitLog.setRequestParam(JSON.toJSONString(map));
        // 创建时间
        visitLog.setCreateTime(new Date());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 获取返回值
        Object body = myResponseBodyStorage.get();
        if (null != body) {
            visitLog.setResponse(JSON.toJSONString(body));
            myResponseBodyStorage.remove();
        }
        // 返回时间
        visitLog.setReturnTime(new Date());
        visitLogService.save(visitLog);
    }
}
