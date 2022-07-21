package com.czg.business.jwt.utils;

import com.czg.common.Result;
import com.czg.common.ResultCodeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@RefreshScope
@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.token.secretKey}")
    private String jwtTokenSecretKey;

    @PostConstruct
    public void init() {
        setStaticJwtTokenSecretKey(jwtTokenSecretKey);
    }

    @Setter
    private static String staticJwtTokenSecretKey;

    public static String createJwt(String id, String subject, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generateKey();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id) // jwt唯一标识，根据业务需要，这个可以设置一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setSubject(subject) // jwt的主体，json格式字符串，可以存放userId、roleId等用户标识信息
                .setIssuer("user") // 颁发着是使用http或https方案的URL（区分大小写），其中包括方案、主机及（可选的）端口号和路径部分
                .setIssuedAt(now) // jwt颁发时间
                .signWith(SignatureAlgorithm.HS256, secretKey); // 签名算法和签名的密钥
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expireDate = new Date(expMillis);
            jwtBuilder.setExpiration(expireDate); // 设置过期时间
        }
        return jwtBuilder.compact();
    }

    private static SecretKey generateKey() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encoderKey = decoder.decode(staticJwtTokenSecretKey);
        return new SecretKeySpec(encoderKey, 0, encoderKey.length, "AES");
    }

    public static Result<Object> validateJwt(String jwtStr) {
        try {
            Claims claims = parseJwt(jwtStr);
            return Result.success(claims);
        } catch (Exception e) {
            log.error("校验jwt失败：{}", e.getMessage(), e);
            return Result.failed(ResultCodeEnum.BUSINESS_ERROR.getCode(), e.getMessage());
        }
    }

    public static Claims parseJwt(String jwt) {
        SecretKey secretKey = generateKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
