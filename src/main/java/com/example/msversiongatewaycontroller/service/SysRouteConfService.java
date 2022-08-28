package com.example.msversiongatewaycontroller.service;

import com.alibaba.fastjson.JSONArray;
import com.example.msversiongatewaycontroller.entity.SysRouteConf;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysql.cj.xdevapi.JsonArray;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pivot
 * @since 2022-08-26
 */
public interface SysRouteConfService extends IService<SysRouteConf> {

    List<SysRouteConf> routes();

    Mono<Void> editRoutes(JSONArray routes);
}
