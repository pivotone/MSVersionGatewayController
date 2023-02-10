package com.example.msversiongatewaycontroller.config;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

    @Resource
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String ROUTE_KEY = "gateway_route_key";
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinition.class));
//        List<RouteDefinition> values = redisTemplate.opsForHash().values(ROUTE_KEY);
        List<RouteDefinition> values = new ArrayList<>();

        List<Object> routes = redisTemplate.opsForHash().values(ROUTE_KEY);

        routes.forEach(route -> {
            RouteDefinition routeDefinition = JSON.parseObject(route.toString(), RouteDefinition.class);
            values.add(routeDefinition);
        });
        log.debug("redis中路由定义条数: {}, {}", values.size(), values);
        return Flux.fromIterable(values);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            log.info("保存路由信息{}", r);
//            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForHash().put(ROUTE_KEY, r.getId(), JSON.toJSONString(r));
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.flatMap(id -> {
            log.info("删除路由信息{}", id);
//            redisTemplate.setKeySerializer(new StringRedisSerializer());
            if(redisTemplate.opsForHash().hasKey(ROUTE_KEY, id)) {
                redisTemplate.opsForHash().delete(ROUTE_KEY, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("Redis中没有该路由: " + id)));
        });
        return Mono.empty();
    }
}
