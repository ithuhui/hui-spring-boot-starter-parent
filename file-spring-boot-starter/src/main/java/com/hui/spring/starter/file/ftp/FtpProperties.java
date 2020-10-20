package com.hui.spring.starter.file.ftp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <code>FtpProperties</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/10/19 21:12.
 *
 * @author Gary.Hu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.ftp")
public class FtpProperties {
    private String host;
    private Integer defaultPort;
    private String username;
    private String password;

    private Integer defaultPoolSize = 10;
    private Integer connectTimeOut = 120 * 1000;
    private String encoding = "UTF-8";
    private Integer bufferSize = 1024;
    private boolean passiveMode = true;
}
