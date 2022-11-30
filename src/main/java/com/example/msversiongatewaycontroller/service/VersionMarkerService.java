package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.VersionMarker;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author pivot
 * @since 2022-11-30
 */
public interface VersionMarkerService extends IService<VersionMarker> {
    String callGetVersionInterval(Map<String, Object> params);
}
