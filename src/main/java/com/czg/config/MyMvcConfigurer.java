package com.czg.config;

import com.czg.business.jwt.interceptor.JwtInterceptor;
import com.czg.interceptor.AllowRepeatInterceptor;
import com.czg.interceptor.VisitLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class MyMvcConfigurer implements WebMvcConfigurer {

    private final AllowRepeatInterceptor allowRepeatInterceptor;

    private final VisitLogInterceptor visitLogInterceptor;

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String url = "/v1/**";
        registry.addInterceptor(jwtInterceptor).addPathPatterns(url).order(-1);
        registry.addInterceptor(visitLogInterceptor).addPathPatterns(url).order(1);
        registry.addInterceptor(allowRepeatInterceptor).addPathPatterns(url).order(10);
    }

}
