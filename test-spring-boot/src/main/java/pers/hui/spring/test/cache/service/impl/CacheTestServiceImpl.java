package pers.hui.spring.test.cache.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.hui.spring.test.cache.service.CacheTestService;

/**
 * <code>CacheTestServiceImpl</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:53.
 *
 * @author _Ken.Hu
 */
@Service
@CacheConfig(cacheNames = "good")
public class CacheTestServiceImpl implements CacheTestService {

    @Override
    @Cacheable(key = "'stringTest'+#key",cacheManager = "kenCacheManager")
    public String stringTest(String key) {
        System.out.println("没有进缓存...........");
        return key + "test";
    }
}
