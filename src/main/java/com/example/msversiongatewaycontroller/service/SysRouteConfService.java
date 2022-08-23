package com.example.msversiongatewaycontroller.service;


import com.example.msversiongatewaycontroller.model.SysRouteConf;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class SysRouteConfService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final boolean STATUS_NORMAL = false;

    public List<SysRouteConf> routes() {
        SysRouteConf conf = new SysRouteConf();
        conf.setDelFlag(STATUS_NORMAL);
        return new ArrayList<SysRouteConf>();
    }
}
