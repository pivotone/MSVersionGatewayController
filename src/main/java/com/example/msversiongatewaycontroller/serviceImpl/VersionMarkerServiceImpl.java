package com.example.msversiongatewaycontroller.serviceImpl;

import com.example.msversiongatewaycontroller.entity.VersionMarker;
import com.example.msversiongatewaycontroller.mapper.VersionMarkerMapper;
import com.example.msversiongatewaycontroller.service.VersionMarkerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author pivot
 * @since 2022-11-30
 */
@Service
public class VersionMarkerServiceImpl extends ServiceImpl<VersionMarkerMapper, VersionMarker> implements VersionMarkerService {
    @Resource
    VersionMarkerMapper versionMarkerMapper;

    @Override
    public String callGetVersionInterval(Map<String, Object> params) {
        return versionMarkerMapper.callGetVersionInterval(params);
    }
}
