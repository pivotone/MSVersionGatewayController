package com.example.msversiongatewaycontroller.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Configuration
public class NacosMetadataConfig {
    @Value("${version}")
    private String version;

    @Resource
    private Environment environment;

    @Bean
    public NacosDiscoveryProperties nacosProperties() throws IOException {
        NacosDiscoveryProperties properties = new NacosDiscoveryProperties();
        Map<String, String> metadata = properties.getMetadata();
        metadata.put("version", version);

        return properties;
    }
}
