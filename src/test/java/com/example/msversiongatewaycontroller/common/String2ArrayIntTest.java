package com.example.msversiongatewaycontroller.common;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@SpringBootTest
class String2ArrayIntTest {

    @Resource
    NacosServiceDiscovery serviceDiscovery;

    @Test
    void testStringVersionToIntArray() {
        VersionStringOp string2ArrayInt = new VersionStringOp();
        String version = "0.17.1";
        int[] versions = string2ArrayInt.stringVersionToIntArray(version);
        System.out.println("major version is " + versions[0] + ", minor version is " + versions[1] + ", patch version is " + versions[2]);
    }

    @Test
    void testCompareVersion() {
        VersionStringOp stringOp = new VersionStringOp();
        String version1 = "0.17.1";
        String version2 = "0.2.1";
        System.out.println("version2 < version1 is " + stringOp.compareVersion(version2, version1));
    }

    @Test
    void testGetVersionInstances() throws IOException, NacosException {
//        String url = "http://39.104.112.98:8848/nacos/v1/ns/instance/list?serviceName=ts-travel-service-v1";
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        CloseableHttpResponse response2 = httpclient.execute(httpGet);
//        System.out.println(response2.toString());

        Content content = Request.get("http://39.104.112.98:8848/nacos/v1/ns/instance/list?serviceName=ts-travel-service-v1")
                .execute().returnContent();

        String serverAddr = "39.104.112.98:8848";
        String namespace = "public";
        Properties properties = new Properties();
        properties.setProperty("serverAddr", serverAddr);
        properties.setProperty("namespace", namespace);

        NamingService naming = NamingFactory.createNamingService(properties);

        List<Instance> allInstances = naming.getAllInstances("ts-seat-service-v1");

        List<ServiceInstance> list = serviceDiscovery.getInstances("ts-seat-service-v1");

        System.out.println(list.get(0).getServiceId());

        System.out.println(allInstances.get(0).toString());

        System.out.println(content);
    }
}