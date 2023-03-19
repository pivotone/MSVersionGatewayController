package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.MService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class MServiceServiceTest {
    @Resource
    MServiceService serviceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MServiceServiceTest.class);

    @Test
    void testSelectByServiceName() {
        MService service = new MService();
        service.setmServiceName("service");
        service = serviceService.selectByServiceName(service);
        LOGGER.info("获取到的serviceId为：" + service.getServiceId());
        assert service.getServiceId() == 3;
    }
}