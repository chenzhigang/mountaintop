package com.czg.interceptor;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.czg.annotation.AllowRepeat;
import com.czg.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllowRepeatInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private final RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求方式
        String methodStr = request.getMethod();
        // 如果是get请求不拦截
        if ("get".equalsIgnoreCase(methodStr) || "delete".equalsIgnoreCase(methodStr)) {
            return true;
        }
        boolean repeatFlag = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AllowRepeat repeat = handlerMethod.getMethod().getAnnotation(AllowRepeat.class);
            if (null == repeat) {
                repeatFlag = true;
            }
        }
        if (repeatFlag) {
            // 查询redis在1分钟内是否存在该请求
            String orgininal;
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            byte[] requestBody = requestWrapper.getBody();
            if (requestBody == null || requestBody.length == 0) {
                orgininal = request.getRequestURI();
            } else {
                orgininal = String.format("%s%s", new String(requestBody, StandardCharsets.UTF_8), request.getRequestURI());
            }
            log.info("orgininal={}", orgininal);
            if (StringUtils.isNotBlank(orgininal)) {
                // 摘要加密
                String redisKey = "request:norepeat:" + DigestUtil.md5Hex(orgininal);
                threadLocal.set(redisKey);
                boolean unRepeatFlag = redisUtil.incrWithExpire(redisKey, 60);
                Assert.isTrue(unRepeatFlag, "重复提交请求，请稍后再试");
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String redisKey = threadLocal.get();
        if (StringUtils.isNotBlank(redisKey)) {
            threadLocal.remove();
            redisUtil.delKey(redisKey);
        }
    }
}
