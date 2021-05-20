package pers.hui.spring.cache.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * <code>RedisCacheMessageListener</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:27.
 *
 * @author _Ken.Hu
 */
@Slf4j
public class RedisCacheMessageListener implements MessageListener {
    private final RedisTemplate<Object, Object> redisTemplate;

    private final KenCacheManager kenCacheManager;

    public RedisCacheMessageListener(RedisTemplate<Object, Object> redisTemplate,
                                     KenCacheManager kenCacheManager) {
        super();
        this.redisTemplate = redisTemplate;
        this.kenCacheManager = kenCacheManager;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisCacheMessage cacheMessage = (RedisCacheMessage) redisTemplate.getValueSerializer().deserialize(message.getBody());
        if (Objects.nonNull(cacheMessage)){
            log.debug("Receive a redis topic message, clear local cache, the cacheName is {}, the key is:[{}]",
                    cacheMessage.getCacheName(), cacheMessage.getKey());
            kenCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
        }
    }
}
