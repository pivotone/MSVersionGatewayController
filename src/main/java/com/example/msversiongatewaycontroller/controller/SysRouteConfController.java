package com.example.msversiongatewaycontroller.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.msversiongatewaycontroller.entity.Result;
import com.example.msversiongatewaycontroller.entity.SysRouteConf;
import com.example.msversiongatewaycontroller.service.SysRouteConfService;
import com.example.msversiongatewaycontroller.utils.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

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
    SysRouteConfService routeConfService;

    @GetMapping("/getAll")
    public Result getAllRouteConf() {
        return ResultUtils.success(routeConfService.routes());
    }

    @GetMapping("/get")
    public Result getRouteConf(@RequestBody Integer id) {
        SysRouteConf conf = new SysRouteConf();
        conf.setDelFlag(false);
        conf.setId(id);
        return ResultUtils.success(routeConfService.getOne(new QueryWrapper<>(conf)));
    }

    @PostMapping("/update")
    public Result updateRouteConf(@RequestBody String confList) {
        JSONArray conf = JSON.parseArray(confList);
        routeConfService.editRoutes(conf);
        return ResultUtils.success();
    }

}
