package com.example.msversiongatewaycontroller.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.example.msversiongatewaycontroller.rule.VersionLoadBalancerRule;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Configuration
@LoadBalancerClients(defaultConfiguration = SpringBeanConfiguration.class)
public class SpringBeanConfiguration {

    @Resource
    NacosDiscoveryProperties properties;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ReactorLoadBalancer<ServiceInstance> versionLoadBalancer(Environment environment,
                                                             LoadBalancerClientFactory clientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new VersionLoadBalancerRule(properties, name,
                clientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
