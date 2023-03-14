package com.example.msversiongatewaycontroller.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.balancer.NacosBalancer;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.example.msversiongatewaycontroller.common.VersionStringOp;
import com.example.msversiongatewaycontroller.service.VersionMarkerService;
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
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.example.msversiongatewaycontroller.filter.VersionGetGlobalFilter.*;


/**
 * @author pivot
 */
public class VersionLoadBalancerRule implements ReactorServiceInstanceLoadBalancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionLoadBalancerRule.class);
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private VersionMarkerService service;

    private final String serviceId;

    @Resource
    NacosServiceDiscovery serviceDiscovery;

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers;

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
            try {
                return this.processInstanceResponse(supplier, serviceInstances);
            } catch (IOException | NacosException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances) throws IOException, NacosException {
        Response<ServiceInstance> serviceInstanceResponse = this.getInstanceResponse(serviceInstances);
        if(supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) throws IOException, NacosException {
        if(instances.isEmpty()) {
            LOGGER.warn("No instance find in this major version");
            return new EmptyResponse();
        }
        LOGGER.info("select instance from this major version and instances is " + instances.size());
        VersionStringOp stringOp = new VersionStringOp();
        List<ServiceInstance> instances1 = new ArrayList<>(instances);
        instances1.addAll(getVersionInstances());
        List<ServiceInstance> sameVersionInstances = instances1.stream().filter(
                    x -> stringOp.compareVersion(x.getMetadata().get("version"), intervals[1])
                            && stringOp.compareVersion(intervals[0], x.getMetadata().get("version"))
                ).collect(Collectors.toList());
        if(sameVersionInstances.isEmpty()) {
            LOGGER.warn("no instance find in this version");
            return new EmptyResponse();
        }
        ServiceInstance instance = getHostByCalWeight(sameVersionInstances);

        LOGGER.info("selected instance is " + instance.getMetadata().get("version"));

        return new DefaultResponse(instance);
    }

    private ServiceInstance getHostByCalWeight(List<ServiceInstance> instances) {
        Map<String, Integer> map = new HashMap<>(instances.size());
        AtomicInteger sumOfAll = new AtomicInteger();
        instances.forEach(x -> {
            String version = x.getMetadata().get("version");
            String[] versions = version.split("\\.");
            int sum = Integer.parseInt(versions[0]) * 10 + Integer.parseInt(versions[1]) * 2 + Integer.parseInt(versions[2]);
            sumOfAll.addAndGet(sum);
            map.put(version, sum);
        });
        Map<String, Double> weightMap = new HashMap<>(instances.size());
        map.forEach((k, v) -> {
            double x = (double) v / sumOfAll.get();
            weightMap.put(k, x);
        });
        Map<Instance, ServiceInstance> instanceMap = new HashMap<>();
        List<Instance> nacosInstance = instances.stream().map(serviceInstance -> {
            Map<String, String> metadata = serviceInstance.getMetadata();
            Instance instance = new Instance();
            instance.setIp(serviceInstance.getHost());
            instance.setPort(serviceInstance.getPort());
            instance.setWeight(Double.parseDouble(String.valueOf(weightMap.get(metadata.get("version")))));
            LOGGER.info(String.valueOf(instance.getWeight()));
            instance.setHealthy(Boolean.parseBoolean(metadata.get("nacos.healthy")));
            instanceMap.put(instance, serviceInstance);
            return instance;
        }).collect(Collectors.toList());

        Instance instance = NacosBalancer.getHostByRandomWeight2(nacosInstance);
        return instanceMap.get(instance);
    }

    private List<ServiceInstance> getVersionInstances() throws IOException, NacosException {
        int min = Integer.parseInt(intervals[0].substring(0, intervals[0].indexOf('.')));
        int max = Integer.parseInt(intervals[1].substring(0, intervals[1].indexOf('.')));
        List<ServiceInstance> lists = new ArrayList<>();
        for(int i = min; i <= max; ++i) {
            if(i == Integer.parseInt(VERSION.substring(1, VERSION.indexOf('.')))) {
                continue;
            }
            String url = SERVICE_NAME +
                    "-v" +
                    i;
            List<ServiceInstance> list = serviceDiscovery.getInstances(url);

            lists.addAll(list);

        }

        return lists;
    }
}