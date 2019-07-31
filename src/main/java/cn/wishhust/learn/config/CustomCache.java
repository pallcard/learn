package cn.wishhust.learn.config;

import cn.wishhust.learn.utils.JsonUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

public class CustomCache<T> {
    private String cacheName;
    private Class<T> cls;
    private StringRedisTemplate stringRedis;


    public void setValue(String key, T value) {

        stringRedis.opsForValue().set(toCacheKey(key), JsonUtils.objectToJson(value));

    }

    public void setValue(String key, T value, Long expiredTime) {
        if (expiredTime < 0) {
            stringRedis.opsForValue().set(toCacheKey(key), JsonUtils.objectToJson(value));
        } else {
            stringRedis.opsForValue().set(toCacheKey(key), JsonUtils.objectToJson(value), expiredTime, TimeUnit.MILLISECONDS);
        }
    }
    public T getValue(String key) {
        if(StringUtils.isEmpty(key))
            return null;
        String s = stringRedis.opsForValue().get(JsonUtils.objectToJson(toCacheKey(key)));

        return JsonUtils.jsonToPojo(s, cls);
    }

    public T getValue(String key, Long expiredTime) {
        if(StringUtils.isEmpty(key))
            return null;
        String s = stringRedis.opsForValue().get(JsonUtils.objectToJson(toCacheKey(key)));
        T value = JsonUtils.jsonToPojo(s, cls);
        if (null != value && expiredTime >= 0) {
            // 设置失效时间
            stringRedis.expire(toCacheKey(key), expiredTime, TimeUnit.MILLISECONDS);
        }
        return value;
    }

    public void deleteKey(String key) {
        stringRedis.delete(toCacheKey(key));
    }

    public Boolean hasKey(String key) {
        return stringRedis.hasKey(toCacheKey(key));
    }

    public Boolean validateKey(String key, Long expiredTime) {
        Boolean res = stringRedis.hasKey(toCacheKey(key));
        if (res && expiredTime > 0) {
            stringRedis.expire(toCacheKey(key), expiredTime, TimeUnit.MILLISECONDS);
        }
        return res;
    }

    private String toCacheKey(String key) {
        return cacheName+"_"+key;
    }
}
