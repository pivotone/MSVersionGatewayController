package com.example.msversiongatewaycontroller.task;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.AbstractEventListener;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
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
