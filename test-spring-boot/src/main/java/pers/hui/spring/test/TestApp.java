package pers.hui.spring.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <code>pers.hui.spring.test.TestApp</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/30 22:24.
 *
 * @author Ken.Hu
 */
@SpringBootApplication(scanBasePackages = "pers.hui.spring")
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }
}
