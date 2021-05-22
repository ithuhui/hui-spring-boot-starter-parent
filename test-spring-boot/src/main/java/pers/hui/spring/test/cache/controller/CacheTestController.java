package pers.hui.spring.test.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pers.hui.spring.test.cache.service.CacheTestService;
import pers.hui.spring.test.cache.dto.CacheDTO;

import java.util.List;

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

    @GetMapping(value = "cacheTest", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(@RequestParam String id) {
        return cacheTestService.stringTest(id);
    }

    @PostMapping(value = "cachePostTest", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CacheDTO> cachePost(@RequestBody CacheDTO cacheDTO) {
        return cacheTestService.cachePost(cacheDTO);
    }

    @GetMapping(value = "cacheEvict", produces = MediaType.APPLICATION_JSON_VALUE)
    public void testEvict() {
        cacheTestService.clear();
    }
}
