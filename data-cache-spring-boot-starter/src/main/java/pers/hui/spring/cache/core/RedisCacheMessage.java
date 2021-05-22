package pers.hui.spring.cache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class RedisCacheMessage implements Serializable {

    private String cacheName;

    private Object key;

    public RedisCacheMessage(String cacheName, Object key) {
        super();
        this.cacheName = cacheName;
        this.key = key;
    }
}
