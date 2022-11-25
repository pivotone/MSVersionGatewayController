package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.MServiceInterface;
import com.example.msversiongatewaycontroller.mapper.MServiceInterfaceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MServiceInterfaceServiceTest {
    @Resource
    MServiceInterfaceMapper interfaceMapper;

    @Test
    void selectInterfaceByMethodAndApi() {
        MServiceInterface serviceInterface = new MServiceInterface();
        serviceInterface.setApi("/api/infos");
        serviceInterface.setVersionId(3);
        serviceInterface.setRequestType("get");
        serviceInterface = interfaceMapper.selectInterfaceByMethodAndApi(serviceInterface);
        System.out.println(serviceInterface == null);
    }
}