package pers.hui.spring.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

/**
 * <code>PermissionConfiguration</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/30 22:13.
 *
 * @author Ken.Hu
 */
@Data
@ConfigurationProperties(prefix = "ken.cache")
public class CacheConfiguration {
    private final Set<String> cacheNames = new HashSet<>();

    /**
     * 是否存储空值，默认true，防止缓存穿透
     */
    private boolean cacheNullValues = true;

    /**
     * 是否动态根据cacheName创建Cache的实现，默认true
     */
    private boolean dynamic = true;

    /**
     * 缓存key的前缀
     */
    private String cachePrefix = "";

    private Redis redis = new Redis();

    private Caffeine caffeine = new Caffeine();


    @Data
    public static class Redis {

        /**
         * 全局过期时间，单位毫秒，默认不过期
         */
        private long defaultExpiration = 0;

        /**
         * 每个cacheName的过期时间，单位毫秒，优先级比defaultExpiration高
         */
        private Map<String, Long> expires = new HashMap<>();

        /**
         * 缓存更新时通知其他节点的topic名称
         */
        private String topic = "cache:redis:caffeine:topic";

    }

    @Data
    public static class Caffeine {

        /**
         * 访问后过期时间，单位毫秒
         */
        private long expireAfterAccess;

        /**
         * 写入后过期时间，单位毫秒
         */
        private long expireAfterWrite;

        /**
         * 写入后刷新时间，单位毫秒
         */
        private long refreshAfterWrite;

        /**
         * 初始化大小
         */
        private int initialCapacity;

        /**
         * 最大缓存对象个数，超过此数量时之前放入的缓存将失效
         */
        private long maximumSize;
    }
}
