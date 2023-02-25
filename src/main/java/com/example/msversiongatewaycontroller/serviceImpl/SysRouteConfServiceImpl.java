package com.example.msversiongatewaycontroller.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.msversiongatewaycontroller.entity.SysRouteConf;
import com.example.msversiongatewaycontroller.mapper.SysRouteConfMapper;
import com.example.msversiongatewaycontroller.service.SysRouteConfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@Service
@AllArgsConstructor
@Slf4j
public class SysRouteConfServiceImpl extends ServiceImpl<SysRouteConfMapper, SysRouteConf> implements SysRouteConfService {
    @Resource
    private SysRouteConfMapper routeConfMapper;

    @Override
    public List<SysRouteConf> routes() {
        List<SysRouteConf> temp = routeConfMapper.selectList(null);
        return new ArrayList<>(temp);
    }

    @Override
    public Integer add(SysRouteConf routeConf) {
        return routeConfMapper.insert(routeConf);
    }

    @Override
    public Integer update(SysRouteConf routeConf) {
        QueryWrapper<SysRouteConf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id", routeConf.getRouteId());
       return routeConfMapper.update(routeConf, queryWrapper);
    }

    @Override
    public Integer delete(String routeId) {
        QueryWrapper<SysRouteConf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id", routeId);
        return routeConfMapper.delete(queryWrapper);
    }

//    @Override
//    public Mono<Void> editRoutes(JSONArray routes) {
//        Boolean result = redisTemplate.delete(ROUTE_KEY);
//        log.info("clear routes {}", result);
//
//        List<RouteDefinition> routeDefinitionList = new ArrayList<>();
//        routes.forEach(value -> {
//            log.info("update info of route -> {}",value);
//            RouteDefinition definition = new RouteDefinition();
//            Map<String, Object> map = (Map) value;
//
//            Object id = map.get("routeId");
//
//            if(id != null) {
//                definition.setId(String.valueOf(id));
//            }
//
//            Object predicates = map.get("predicates");
//            if(predicates != null) {
//                JSONArray predicatesArray = (JSONArray) predicates;
//                List<PredicateDefinition> predicateDefinitionList =
//                        predicatesArray.toJavaList(PredicateDefinition.class);
//                definition.setPredicates(predicateDefinitionList);
//            }
//
//            Object filters = map.get("filters");
//            if(filters != null) {
//                JSONArray filtersArray = (JSONArray) filters;
//                List<FilterDefinition> filterDefinitionList =
//                        filtersArray.toJavaList(FilterDefinition.class);
//                definition.setFilters(filterDefinitionList);
//            }
//
//            Object uri = map.get("uri");
//            if(uri != null) {
//                definition.setUri(URI.create(String.valueOf(uri)));
//            }
//
//            Object order = map.get("order");
//            if(order != null) {
//                definition.setOrder(Integer.parseInt(String.valueOf(order)));
//
//            }
//
//            redisTemplate.setHashValueSerializer(new
//                    Jackson2JsonRedisSerializer<>(RouteDefinition.class));
//            redisTemplate.opsForHash().put(ROUTE_KEY, definition.getId(), definition);
//            routeDefinitionList.add(definition);
//        });
//
//        // 修改删除逻辑
//        SysRouteConf conf = new SysRouteConf();
//        conf.setDelFlag(!STATUS_NORMAL);
//        this.remove(new QueryWrapper<>(conf));
//
//        List<SysRouteConf> routeConfList = routeDefinitionList.stream().map(definition -> {
//            SysRouteConf routeConf = new SysRouteConf();
//            routeConf.setRouteId(definition.getId());
//            routeConf.setFilters(definition.getFilters().toString());
//            routeConf.setPredicates(definition.getPredicates().toString());
//            routeConf.setOrder(definition.getOrder());
//            routeConf.setUri(definition.getUri().toString());
//            return routeConf;
//        }).collect(Collectors.toList());
//        this.saveOrUpdateBatch(routeConfList);
//        log.info("update over");
//
//        // 添加发布事件，提醒服务去更改信息
//        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
//        return Mono.empty();
//    }
//
//    public static byte[] ToByte(Object object) {
//        try {
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            ObjectOutputStream outputStream = new ObjectOutputStream(out);
//            outputStream.writeObject(object);
//            byte[] bytes = out.toByteArray();
//            outputStream.close();
//            return bytes;
//        } catch (Exception e) {
//            log.info("ObjectBlob create failed");
//            return null;
//        }
//    }
}