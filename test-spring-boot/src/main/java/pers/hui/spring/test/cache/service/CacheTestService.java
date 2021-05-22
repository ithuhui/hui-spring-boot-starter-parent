package pers.hui.spring.test.cache.service;

import pers.hui.spring.test.cache.dto.CacheDTO;

import java.util.List;

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

    List<CacheDTO> cachePost(CacheDTO cacheDTO);

    void clear();
}
