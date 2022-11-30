package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.VersionInterval;
import com.example.msversiongatewaycontroller.entity.VersionMarker;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author pivot
 * @since 2022-11-30
 */
public interface VersionMarkerService extends IService<VersionMarker> {
    VersionInterval callGetVersionInterval(int major, int minor, int patch, String url,
                                           String requestType, VersionInterval interval);
}
