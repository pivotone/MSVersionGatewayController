package com.example.msversiongatewaycontroller.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

    private final RedisTemplate redisTemplate;
    private static final String ROUTE_KEY = "gateway_route_key";
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinition.class));
        List<RouteDefinition> values = redisTemplate.opsForHash().values(ROUTE_KEY);
        log.debug("redis中路由定义条数: {}, {}", values.size(), values);
        return Flux.fromIterable(values);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            log.info("保存路由信息{}", r);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForHash().put(ROUTE_KEY, r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            log.info("删除路由信息{}", id);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForHash().delete(ROUTE_KEY, id);
        });
        return Mono.empty();
    }
}
