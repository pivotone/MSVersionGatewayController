package com.example.msversiongatewaycontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsVersionGatewayControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsVersionGatewayControllerApplication.class, args);
    }

}
