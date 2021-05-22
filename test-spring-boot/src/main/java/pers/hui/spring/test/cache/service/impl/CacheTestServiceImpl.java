package pers.hui.spring.test.cache.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.hui.spring.test.cache.service.CacheTestService;
import pers.hui.spring.test.cache.dto.CacheDTO;

import java.util.Arrays;
import java.util.List;

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
@CacheConfig(cacheNames = "good", cacheManager = "kenCacheManager")
public class CacheTestServiceImpl implements CacheTestService {

    @Override
    @Cacheable(key = "'stringTest'+#key")
    public String stringTest(String key) {
        System.out.println("没有进缓存...........");
        return key + "test";
    }

    @Override
    @Cacheable(key = "'postTest:orderId:'+#cacheDTO.orderId+':userId:'+#cacheDTO.userId+':amount:'+#cacheDTO.amount.val")
    public List<CacheDTO> cachePost(CacheDTO cacheDTO) {
        System.out.println("没有进缓存...........");
        return Arrays.asList(cacheDTO, cacheDTO);
    }

    @Override
    @CacheEvict(key = "'postTest:orderId:1234:userId:123:amount:1.199'")
    public void clear() {

    }
}
