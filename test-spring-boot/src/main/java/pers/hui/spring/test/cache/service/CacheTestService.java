package pers.hui.spring.test.cache.service;

import org.springframework.cache.annotation.CacheConfig;

/**
 * <code>CacheTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:45.
 *
 * @author _Ken.Hu
 */
public interface CacheTestService {

    String stringTest(String key);
}
