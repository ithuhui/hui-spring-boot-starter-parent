package pers.hui.spring.cache.core;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pers.hui.spring.cache.config.CacheConfiguration;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <code>KenCache</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:00.
 *
 * @author _Ken.Hu
 */
@Slf4j
public class KenCache extends AbstractValueAdaptingCache {

    private String name;

    private RedisTemplate<Object, Object> redisTemplate;

    private Cache<Object, Object> caffeineCache;

    private String cachePrefix;

    private long defaultExpiration = 0;

    private Map<String, Long> expires;

    private String topic = "cache:redis:caffeine:topic";

    protected KenCache(boolean allowNullValues) {
        super(allowNullValues);
    }

    public KenCache(String name, RedisTemplate<Object, Object> redisTemplate,
                    Cache<Object, Object> caffeineCache, CacheConfiguration cacheConfiguration) {
        super(cacheConfiguration.isCacheNullValues());
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.caffeineCache = caffeineCache;
        this.cachePrefix = cacheConfiguration.getCachePrefix();
        this.defaultExpiration = cacheConfiguration.getRedis().getDefaultExpiration();
        this.expires = cacheConfiguration.getRedis().getExpires();
        this.topic = cacheConfiguration.getRedis().getTopic();
    }


    @Override
    protected Object lookup(@NonNull Object key) {
        String cacheKey = (String) getKey(key);
        Object value = caffeineCache.getIfPresent(cacheKey);
        if (Objects.nonNull(value)) {
            log.debug("Get cache from caffeine, the key [{}]", cacheKey);
            return value;
        }

        value = redisTemplate.opsForValue().get(cacheKey);

        if (Objects.nonNull(value)) {
            log.debug("Get cache from redis and put in caffeine, the key is:[{}]", cacheKey);
            caffeineCache.put(cacheKey, value);
        }
        return value;
    }

    @Override
    @NonNull
    public String getName() {
        return this.name;
    }

    @Override
    @NonNull
    public Object getNativeCache() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        }
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            value = lookup(key);
            if (value != null) {
                return (T) value;
            }
            value = valueLoader.call();
            Object storeValue = toStoreValue(valueLoader.call());
            put(key, storeValue);
            return (T) value;
        } catch (Exception e) {
            try {
                throw new ValueRetrievalException(key, valueLoader, e.getCause());
            } catch (Exception e1) {
                throw new IllegalStateException(e1);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(@NonNull Object key, Object value) {
        if (!super.isAllowNullValues() && Objects.isNull(value)) {
            this.evict(key);
            return;
        }
        putExpireKey(key, value);

        pushMsgToClearCache(new RedisCacheMessage(this.name, key));

        caffeineCache.put(key, value);
    }

    @Override
    public void evict(@NonNull Object key) {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
        redisTemplate.delete(getKey(key));

        pushMsgToClearCache(new RedisCacheMessage(this.name, key));

        caffeineCache.invalidate(key);
    }

    /**
     * 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
     */
    @Override
    public void clear() {
        Set<Object> keys = redisTemplate.keys(this.name.concat(":"));
        if (!CollectionUtils.isEmpty(keys)) {

            keys.parallelStream().forEach(key ->
                    redisTemplate.delete(key)
            );

            pushMsgToClearCache(new RedisCacheMessage(this.name, null));

            caffeineCache.invalidateAll();
        }
    }

    private Object getKey(Object key) {
        return this.name.concat(":").concat(StringUtils.isEmpty(cachePrefix) ? key.toString() : cachePrefix.concat(":").concat(key.toString()));
    }

    /**
     * 缓存变更时通知其他节点清理本地缓存
     *
     * @param message 推送的消息
     */
    private void pushMsgToClearCache(RedisCacheMessage message) {
        redisTemplate.convertAndSend(topic, message);
    }


    private long getExpire() {
        long expire = defaultExpiration;
        Long cacheNameExpire = expires.get(this.name);
        return cacheNameExpire == null ? expire : cacheNameExpire;
    }

    @Override
    public ValueWrapper putIfAbsent(@NonNull Object key, Object value) {
        Object cacheKey = getKey(key);
        Object prevValue;
        // 考虑使用分布式锁，或者将redis的setIfAbsent改为原子性操作
        synchronized (this) {
            prevValue = redisTemplate.opsForValue().get(cacheKey);
            if (Objects.isNull(prevValue)) {
                putExpireKey(key, value);
            }
        }
        return toValueWrapper(prevValue);
    }

    /**
     * 清理本地缓存
     *
     * @param key
     */
    public void clearLocal(Object key) {
        log.debug("Clear local cache, the key is:[{}]", key);
        if (Objects.nonNull(key)) {
            caffeineCache.invalidate(key);
        }
    }

    private void putExpireKey(Object key, Object value) {
        long expire = getExpire();
        if (expire > 0) {
            redisTemplate.opsForValue().set(getKey(key), toStoreValue(value), expire, TimeUnit.MILLISECONDS);
        } else {
            redisTemplate.opsForValue().set(getKey(key), toStoreValue(value));
        }

        pushMsgToClearCache(new RedisCacheMessage(this.name, key));

        caffeineCache.put(key, toStoreValue(value));
    }
}
