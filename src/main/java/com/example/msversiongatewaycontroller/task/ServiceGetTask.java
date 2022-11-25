package com.example.msversiongatewaycontroller.task;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.AbstractEventListener;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.example.msversiongatewaycontroller.entity.MService;
import com.example.msversiongatewaycontroller.entity.MServiceVersion;
import com.example.msversiongatewaycontroller.service.MServiceService;
import com.example.msversiongatewaycontroller.service.MServiceVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

@Component
public class ServiceGetTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceGetTask.class);

    @Resource
    private DiscoveryClient client;

    @Resource
    MServiceService serviceService;

    @Resource
    MServiceVersionService versionService;

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    private final Map<String, Boolean> serviceMap = new ConcurrentHashMap<>();

    @Scheduled(initialDelay = 1000, fixedDelay = 5 * 1000)
    public void scheduleServiceTask() throws IOException, NacosException {
        List<String> serviceNameLists = client.getServices();
        setFalse(serviceNameLists);
        LOGGER.info(serviceNameLists.toString());
        String serverAddr = "39.104.112.98:8848";
        String namespace = "public";
        Properties properties = new Properties();
        properties.setProperty("serverAddr", serverAddr);
        properties.setProperty("namespace", namespace);
        NamingService naming = NamingFactory.createNamingService(properties);
        for(String name : serviceNameLists) {
            if(serviceMap.containsKey(name) && serviceMap.get(name)) {
                continue;
            }
            serviceMap.put(name, true);
            Executor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread thread = new Thread(r);
                            thread.setName(name);
                            return thread;
                        }
                    });
            naming.subscribe(name, new AbstractEventListener() {
                @Override
                public Executor getExecutor() {
                    return executor;
                }

                @Override
                public void onEvent(Event event) {
                    LOGGER.info("订阅到的服务：" + ((NamingEvent) event).getServiceName());
                    LOGGER.info("订阅到的实例：" + ((NamingEvent) event).getInstances());
                    MService service = new MService();
                    String name = ((NamingEvent) event).getServiceName();
                    name = name.replaceAll("-v[0-9]*", "");
                    service.setmServiceName(name);
                    service.setDescription("A mirco service instance about " + service.getmServiceName());
                    service.setHealth(1);
                    MService tempService = serviceService.selectByServiceName(service);
                    if(tempService == null) {
                        serviceService.save(service);
                        service = serviceService.selectByServiceName(service);
                    } else {
                        service = tempService;
                    }
                    int serviceId = service.getServiceId();
                    LOGGER.info("获得的service id为：" + serviceId);
                    List<Instance> instances = ((NamingEvent) event).getInstances();
                    for(Instance instance : instances) {
                        Map<String, String> map = instance.getMetadata();
                        String version = map.get("version");
                        String[] versions = version.split("\\.");
                        MServiceVersion serviceVersion = new MServiceVersion(serviceId, Integer.parseInt(versions[0]),
                                Integer.parseInt(versions[1]), Integer.parseInt(versions[2]));
                        MServiceVersion tempServiceVersion = versionService.selectByVersionAndService(serviceVersion);
                        if(tempServiceVersion == null) {
                            versionService.save(serviceVersion);
                        }
                    }
                }
            });
        }
    }
    private void setFalse(List<String> serviceNameLists) {
        serviceMap.replaceAll((k, v) -> false);
        for(String name : serviceNameLists)
            if(serviceMap.containsKey(name))
                serviceMap.put(name, true);
    }
}
