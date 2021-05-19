package pers.hui.spring.test.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.hui.spring.test.cache.service.CacheTestService;

/**
 * <code>CacheTestController</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/19 22:44.
 *
 * @author _Ken.Hu
 */
@RestController
public class CacheTestController {

    private final CacheTestService cacheTestService;

    @Autowired
    public CacheTestController(CacheTestService cacheTestService) {
        this.cacheTestService = cacheTestService;
    }

    @GetMapping("cacheTest")
    public String test(String id){
        return cacheTestService.stringTest(id);
    }

}
