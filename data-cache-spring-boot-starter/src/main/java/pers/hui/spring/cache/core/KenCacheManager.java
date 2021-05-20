package pers.hui.spring.cache.core;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import pers.hui.spring.cache.config.CacheConfiguration;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * <code>KenCacheManager</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:21.
 *
 * @author _Ken.Hu
 */
@Slf4j
public class KenCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

    private final CacheConfiguration cacheConfiguration;

    private final RedisTemplate<Object, Object> redisTemplate;

    private boolean dynamic = true;

    private final Set<String> cacheNames;

    public KenCacheManager(CacheConfiguration cacheConfiguration,
                           RedisTemplate<Object, Object> redisTemplate) {
        super();
        this.cacheConfiguration = cacheConfiguration;
        this.redisTemplate = redisTemplate;
        this.dynamic = cacheConfiguration.isDynamic();
        this.cacheNames = cacheConfiguration.getCacheNames();
    }

    @Override
    public Cache getCache(@NonNull String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic && !cacheNames.contains(name)) {
            return cache;
        }

        cache = new KenCache(name, redisTemplate, caffeineCache(), cacheConfiguration);
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        log.debug("Create cache instance, the cache name is : {}", name);
        return oldCache == null ? cache : oldCache;
    }

    public com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache() {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
        if (cacheConfiguration.getCaffeine().getExpireAfterAccess() > 0) {
            cacheBuilder.expireAfterAccess(cacheConfiguration.getCaffeine().getExpireAfterAccess(), TimeUnit.MILLISECONDS);
        }
        if (cacheConfiguration.getCaffeine().getExpireAfterWrite() > 0) {
            cacheBuilder.expireAfterWrite(cacheConfiguration.getCaffeine().getExpireAfterWrite(), TimeUnit.MILLISECONDS);
        }
        if (cacheConfiguration.getCaffeine().getInitialCapacity() > 0) {
            cacheBuilder.initialCapacity(cacheConfiguration.getCaffeine().getInitialCapacity());
        }
        if (cacheConfiguration.getCaffeine().getMaximumSize() > 0) {
            cacheBuilder.maximumSize(cacheConfiguration.getCaffeine().getMaximumSize());
        }
        if (cacheConfiguration.getCaffeine().getRefreshAfterWrite() > 0) {
            cacheBuilder.refreshAfterWrite(cacheConfiguration.getCaffeine().getRefreshAfterWrite(), TimeUnit.MILLISECONDS);
        }
        cacheBuilder.removalListener((key, value, removalCause) ->
                log.debug("Remove caffeine cache [key:{},value:{}]，reason:{}", key, value, removalCause)
        );
        return cacheBuilder.build();
    }

    @Override
    @NonNull
    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }

    public void clearLocal(String cacheName, Object key) {
        Cache cache = cacheMap.get(cacheName);
        if (Objects.isNull(cache)) {
            return;
        }
        KenCache redisCaffeineCache = (KenCache) cache;
        redisCaffeineCache.clearLocal(key);
    }
}
