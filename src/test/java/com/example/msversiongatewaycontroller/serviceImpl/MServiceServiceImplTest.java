package com.example.msversiongatewaycontroller.serviceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MServiceServiceImplTest {
    @Autowired
    private MServiceServiceImpl mServiceService;

    @Test
    void getMService(){
        System.out.println(mServiceService.getById(1).toString());
    }

}