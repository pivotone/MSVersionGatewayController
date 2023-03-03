package com.example.msversiongatewaycontroller.service;

import com.alibaba.fastjson.JSONArray;
import com.example.msversiongatewaycontroller.entity.SysRouteConf;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
public interface SysRouteConfService extends IService<SysRouteConf> {

    /**
     * get list of route
     * @return list of route
     */
    List<SysRouteConf> routes();

    /**
     * add new route configuration
     * @param routeConf new route configuration
     * @return 0 or 1
     */
    Integer add(SysRouteConf routeConf);

    /**
     * update a exist route configuration
     * @param routeConf need updating route
     * @return 0 or 1
     */
    Integer update(SysRouteConf routeConf);

    /**
     * delete a exist route configuration
     * @param id need deleting db's id
     * @return 0 or 1
     */
    Integer delete(int id);
}
