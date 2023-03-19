package com.example.msversiongatewaycontroller.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class VersionMarkerServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionMarkerServiceTest.class);

    @Resource
    VersionMarkerService versionMarkerService;

    @Test
    void callGetVersionInterval() {
        String url = "/api/infos";
        Map<String, Object> params = new HashMap<>();
        params.put("major", 0);
        params.put("minor", 0);
        params.put("patch", 2);
        params.put("url", url);
        params.put("requestType", "get");
        versionMarkerService.callGetVersionInterval(params);
        LOGGER.info(url + " and get is left version :" + params.get("leftVersion") + ", right version :" + params.get("rightVersion"));
        params.put("requestType", "post");
        versionMarkerService.callGetVersionInterval(params);
        LOGGER.info(url + " and post is left version :" + params.get("leftVersion") + ", right version :" + params.get("rightVersion"));
    }
}