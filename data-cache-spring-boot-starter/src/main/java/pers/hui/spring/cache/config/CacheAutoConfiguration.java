package pers.hui.spring.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import pers.hui.spring.cache.core.KenCacheManager;
import pers.hui.spring.cache.core.RedisCacheMessageListener;

import java.util.Objects;

/**
 * <code>PermissionAutoConfiguration</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/30 22:13.
 *
 * @author Ken.Hu
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties({CacheConfiguration.class})
public class CacheAutoConfiguration {
    private final CacheConfiguration cacheConfiguration;

    @Autowired
    public CacheAutoConfiguration(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    @Primary
    public KenCacheManager kenCacheManager(RedisTemplate<Object, Object> redisTemplate) {
        log.debug("Init cache manager successful");
        return new KenCacheManager(cacheConfiguration, redisTemplate);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisTemplate<Object, Object> redisTemplate,
                                                                       KenCacheManager kenCacheManager) {
        log.debug("Init redisMessageListener successful");
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        RedisCacheMessageListener cacheMessageListener = new RedisCacheMessageListener(redisTemplate, kenCacheManager);
        redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic(cacheConfiguration.getRedis().getTopic()));
        return redisMessageListenerContainer;
    }
}
