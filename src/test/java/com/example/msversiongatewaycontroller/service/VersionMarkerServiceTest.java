package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.VersionInterval;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VersionMarkerServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionMarkerServiceTest.class);

    @Resource
    VersionMarkerService versionMarkerService;

    @Test
    void callGetVersionInterval() {
        String url = "/api/infos";
        VersionInterval interval = versionMarkerService.callGetVersionInterval(0, 0, 1, url, "get", new VersionInterval());
        LOGGER.info(url + " and get " + interval.getLeft() + " , " + interval.getRight());
        interval = versionMarkerService.callGetVersionInterval(0, 0, 1, url, "post", new VersionInterval());
        LOGGER.info(url + " and post " + interval.getLeft() + " , " + interval.getRight());
    }
}