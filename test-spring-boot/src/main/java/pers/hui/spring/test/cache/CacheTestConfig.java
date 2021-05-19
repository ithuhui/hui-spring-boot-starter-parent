package pers.hui.spring.test.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * <code>CacheTestConfig</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:55.
 *
 * @author _Ken.Hu
 */
@Configuration
public class CacheTestConfig {
    public CacheTestConfig() {
        System.out.println("enable cache .........");
    }
}
