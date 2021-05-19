package pers.hui.spring.cache.core;

import lombok.Data;

import java.io.Serializable;

/**
 * <code>RedisCacheMessage</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:09.
 *
 * @author _Ken.Hu
 */
@Data
public class RedisCacheMessage implements Serializable {

    private final String cacheName;

    private final Object key;

    public RedisCacheMessage(String cacheName, Object key) {
        super();
        this.cacheName = cacheName;
        this.key = key;
    }
}
