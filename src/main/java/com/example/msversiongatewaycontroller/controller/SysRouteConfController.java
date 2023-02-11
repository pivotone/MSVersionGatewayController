package com.example.msversiongatewaycontroller.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.msversiongatewaycontroller.entity.Result;
import com.example.msversiongatewaycontroller.entity.SysRouteConf;
import com.example.msversiongatewaycontroller.handler.GatewayServiceHandler;
import com.example.msversiongatewaycontroller.service.SysRouteConfService;
import com.example.msversiongatewaycontroller.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@Controller
@RequestMapping("/msversiongatewaycontroller/sysRouteConf")
public class SysRouteConfController {

    @Resource
    private GatewayServiceHandler serviceHandler;
    @Resource
    SysRouteConfService routeConfService;

    @GetMapping("/routes")
    public Mono<Result> getAllRouteConf() {
        return Mono.just(ResultUtils.success(routeConfService.routes()));
    }

    @GetMapping("/route")
    public Mono<Result> getRouteConf(@RequestBody SysRouteConf routeConf) {
        return Mono.just(ResultUtils.success(routeConfService.getOne(new QueryWrapper<>(routeConf))));
    }

    @PostMapping("/route")
    public Mono<Result> updateRouteConfs(@RequestBody List<SysRouteConf> routeConfs) {
        routeConfs.forEach(routeConf -> {
            if(routeConf.getId() != null) {
                this.serviceHandler.updateRoute(routeConf);
                this.routeConfService.update(routeConf);
            } else {
                this.routeConfService.add(routeConf);
                this.serviceHandler.saveRoute(routeConf);
            }
        });
        return Mono.just(ResultUtils.success());
    }

    @DeleteMapping("/route")
    public Mono<Result> deleteRouteConf(@RequestBody String routeId) {
        this.serviceHandler.deleteRoute(routeId);
        this.routeConfService.delete(routeId);
        return Mono.just(ResultUtils.success());
    }

}
