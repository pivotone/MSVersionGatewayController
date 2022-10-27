package com.example.msversiongatewaycontroller.rule;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.balancer.NacosBalancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;


import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.msversiongatewaycontroller.filter.VersionGetGlobalFilter.VERSION;


public class VersionLoadBalancerRule implements ReactorServiceInstanceLoadBalancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionLoadBalancerRule.class);
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    private final String serviceId;

    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers;

    public VersionLoadBalancerRule(NacosDiscoveryProperties nacosDiscoveryProperties,
                                   String serviceId,
                                   ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
        this.serviceId = serviceId;
        this.serviceInstanceListSuppliers = serviceInstanceListSuppliers;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSuppliers
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map((serviceInstances) -> {
            return this.processInstanceResponse(supplier, serviceInstances);
        });
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = this.getInstanceResponse(serviceInstances);
        if(supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if(instances.isEmpty()) {
            LOGGER.warn("No instance find in this major version");
            return new EmptyResponse();
        }
        LOGGER.info("select instance from this major version and instances is " + instances.size());
        List<ServiceInstance> sameVersionInstances = instances.stream().filter(
                        x -> StringUtils.equals(x.getMetadata().get("version"), VERSION.replace("v", "")))
                .collect(Collectors.toList());
        if(sameVersionInstances.isEmpty()) {
            LOGGER.warn("no instance find in this version");
            return new EmptyResponse();
        }
        ServiceInstance instance = NacosBalancer.getHostByRandomWeight3(sameVersionInstances);

        LOGGER.info("selected instance is " + instance.getMetadata().get("version"));

        return new DefaultResponse(instance);
    }
}