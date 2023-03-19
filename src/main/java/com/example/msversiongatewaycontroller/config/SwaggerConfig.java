package com.example.msversiongatewaycontroller.config;

import com.example.msversiongatewaycontroller.entity.InfoProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Resource
    InfoProperties info;

    @Value("${spring.application.name}")
    private String title;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(info.isEnable())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.msversiongatewaycontroller.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Hou ShengMing", "https://github.com/pivotone", "18865290907@163.com");
        return new ApiInfo(
                title,
                title + " api description",
                info.getVersion(),
                "http://localhost:8848",
                contact,
                "Apache2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }

}
