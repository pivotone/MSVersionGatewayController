package com.example.msversiongatewaycontroller.handler;

import com.example.msversiongatewaycontroller.entity.SysRouteConf;
import com.example.msversiongatewaycontroller.mapper.SysRouteConfMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author pivot
 * @date 2023/02/10
 */
@Component
public class GatewayServiceHandler implements ApplicationEventPublisherAware, CommandLineRunner {

    private static final String ROUTE_KEY = "gateway_route_key";

    private ApplicationEventPublisher publisher;

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServiceHandler.class);

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Resource
    private SysRouteConfMapper routeConfMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void run(String... args) throws Exception {
        this.loadRoute();
    }

    public void loadRoute() {
        LOGGER.info("start load route information");

        redisTemplate.delete(ROUTE_KEY);

        List<SysRouteConf> routeConfs = routeConfMapper.selectList(null);
        routeConfs.forEach(sysRouteConf -> {
            RouteDefinition definition = handleData(sysRouteConf);

            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        });

        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void saveRoute(SysRouteConf routeConf) {
        RouteDefinition definition = handleData(routeConf);

        routeDefinitionWriter.save(Mono.just(definition)).subscribe();

        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void updateRoute(SysRouteConf routeConf) {
        SysRouteConf sysRouteConf = new SysRouteConf();
        BeanUtils.copyProperties(routeConf, sysRouteConf);

        RouteDefinition routeDefinition = handleData(sysRouteConf);

        routeDefinitionWriter.delete(Mono.just(routeConf.getRouteId())).subscribe();

        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();

        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void deleteRoute(String routeId) {
        routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();

        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        LOGGER.info("delete is finish.");
    }

    public RouteDefinition handleData(SysRouteConf routeConf) {
        RouteDefinition routeDefinition = new RouteDefinition();

        URI uri = null;

        if(!routeConf.getUri().startsWith("http")) {
            if(!routeConf.getUri().startsWith("lb")) {
                uri = UriComponentsBuilder.fromUriString("lb://" + routeConf.getUri()).build().toUri();
            } else {
                uri = UriComponentsBuilder.fromUriString(routeConf.getUri()).build().toUri();
            }
        } else {
            uri = UriComponentsBuilder.fromHttpUrl(routeConf.getUri()).build().toUri();
        }
        routeDefinition.setId(routeConf.getRouteId());

        routeDefinition.setUri(uri);

        PredicateDefinition predicate = new PredicateDefinition(routeConf.getPredicates());

        routeDefinition.setPredicates(Collections.singletonList(predicate));

        if(routeConf.getFilters() != null) {

            FilterDefinition filter = new FilterDefinition(routeConf.getFilters());

            routeDefinition.setFilters(Collections.singletonList(filter));
        } else {
            routeConf.setFilters(null);
        }

        routeDefinition.setOrder(routeConf.getOrders());

        return routeDefinition;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
