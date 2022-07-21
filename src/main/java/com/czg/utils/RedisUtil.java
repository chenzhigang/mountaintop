package com.czg.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * redis工具类
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * lua脚本保证原子化操作
     */
    private static final String SCRIPT_INCR_EXPIRE = "local current = redis.call('incr',KEYS[1]);" +
            " if  current == 1 then" +
            " redis.call('expire',KEYS[1],ARGV[1])" +
            " end;" +
            " return current";

    private static final DefaultRedisScript<Long> INCR_SCRIPT = new DefaultRedisScript<>();

    static {
        INCR_SCRIPT.setResultType(Long.class);
        INCR_SCRIPT.setScriptText(SCRIPT_INCR_EXPIRE);
    }

    /**
     * 自增判断是否重复请求
     *
     * @param key redis键
     * @param expire 过期时间
     * @return 是否重复请求：false是，true否
     */
    public boolean incrWithExpire(String key, int expire) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        Long num = redisTemplate.execute(INCR_SCRIPT, keys, expire);
        return null != num && num == 1;
    }

    /**
     * 删除redis键值
     *
     * @param keys 键值数组
     */
    public void delKey(String... keys) {
        if (null == keys || keys.length == 0) {
            return;
        }
        redisTemplate.delete(Arrays.asList(keys));
    }

}
