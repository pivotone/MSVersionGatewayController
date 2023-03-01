package com.example.msversiongatewaycontroller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.msversiongatewaycontroller.entity.Result;
import com.example.msversiongatewaycontroller.entity.SysRouteConf;
import com.example.msversiongatewaycontroller.handler.GatewayServiceHandler;
import com.example.msversiongatewaycontroller.service.SysRouteConfService;
import com.example.msversiongatewaycontroller.utils.ResultUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@RestController
@RequestMapping
public class SysRouteConfController {

    @Resource
    private GatewayServiceHandler serviceHandler;
    @Resource
    SysRouteConfService routeConfService;

    @GetMapping("/refresh")
    public Mono<Result> refresh() {
        this.serviceHandler.loadRoute();
        return Mono.just(ResultUtils.success());
    }

    @GetMapping("/routes")
    public Mono<Result> getAllRouteConf() {
        return Mono.just(ResultUtils.success(routeConfService.routes()));
    }

    @GetMapping("/route")
    public Mono<Result> getRouteConf(@RequestParam("routeId") String routeId) {
        SysRouteConf routeConf = new SysRouteConf();
        routeConf.setRouteId(routeId);
        return Mono.just(ResultUtils.success(routeConfService.getOne(new QueryWrapper<>(routeConf))));
    }

    @PostMapping(value = "/route", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> addRouteConfs(@RequestBody SysRouteConf routeConf) {
        routeConf.setCreateTime(new Date());
        this.serviceHandler.saveRoute(routeConf);
        this.routeConfService.add(routeConf);
        return Mono.just(ResultUtils.success());
    }

    @PutMapping(value = "/route",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> updateRouteConfs(@RequestBody SysRouteConf routeConf) {
        routeConf.setUpdateTime(new Date());
        this.routeConfService.update(routeConf);
        this.serviceHandler.updateRoute(routeConf);

        return Mono.just(ResultUtils.success());
    }

    @DeleteMapping(value = "/route", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> deleteRouteConf(@RequestBody SysRouteConf sysRouteConf) {
        this.serviceHandler.deleteRoute(sysRouteConf.getRouteId());
        this.routeConfService.delete(sysRouteConf.getId());
        return Mono.just(ResultUtils.success());
    }

}
