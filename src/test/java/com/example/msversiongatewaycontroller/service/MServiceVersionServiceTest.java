package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.MServiceVersion;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MServiceVersionServiceTest {
    @Resource
    MServiceVersionService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(MServiceVersionServiceTest.class);

    @Test
    void selectByVersionAndServiceTest() {
        MServiceVersion version = new MServiceVersion(2, 0, 0, 1);
        version = service.selectByVersionAndService(version);
        LOGGER.info("获取到的信息为：" + version.toString());
    }
}