package com.hui.spring.starter.file.ceph;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <code>CephConfig</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/10/19 21:06.
 *
 * @author Gary.Hu
 */
@Configuration
@EnableConfigurationProperties({CephProperties.class})
@ConditionalOnClass(AmazonS3.class)
public class CephConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "file.ceph.service", name = "enable", havingValue = "true")
    public AmazonS3 amazonS3(CephProperties cephProperties) {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(cephProperties.getEndpoint(), cephProperties.getRegion());

        AWSCredentials credentials = new BasicAWSCredentials(cephProperties.getAccessKey(), cephProperties.getSecretKey());

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        clientConfig.setSocketTimeout(1200 * 1000);
        clientConfig.setMaxErrorRetry(3);
        clientConfig.setConnectionTimeout(1200 * 1000);

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(clientConfig)
                .enablePathStyleAccess()
                .build();
    }
}
