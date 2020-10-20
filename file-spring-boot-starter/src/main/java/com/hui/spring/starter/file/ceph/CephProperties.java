package com.hui.spring.starter.file.ceph;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <code>CephProperties</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/10/19 20:58.
 *
 * @author Gary.Hu
 */
@ConfigurationProperties(prefix = "file.ceph.service")
@Data
public class CephProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String defaultBucket;
    private String prefix;
    private String accessUrl;
    private String region;
}
