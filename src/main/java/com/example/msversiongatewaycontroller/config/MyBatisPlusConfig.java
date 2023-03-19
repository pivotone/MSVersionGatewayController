package com.example.msversiongatewaycontroller.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MyBatisPlusConfig {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/MSversion", "root", "Hsm1@138")
                .globalConfig(builder -> {
                    builder.author("pivot")
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")
                            .enableSwagger()
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder -> {
                    builder.parent("com.example")
                            .moduleName("msversiongatewaycontroller")
                            .mapper("mapper")
                            .xml("mapper")
                            .service("service")
                            .serviceImpl("serviceImpl")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("version_marker")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
