package pers.hui.spring.starter.file.config;

import pers.hui.spring.starter.file.ceph.CephConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

/**
 * <code>FileServiceAutoConfiguration</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/10/19 22:56.
 *
 * @author Gary.Hu
 */
@Configuration
@ConditionalOnWebApplication
@ImportAutoConfiguration({CephConfiguration.class})
public class FileServiceAutoConfiguration {

}
