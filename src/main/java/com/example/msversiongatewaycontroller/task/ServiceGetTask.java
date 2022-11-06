package com.example.msversiongatewaycontroller.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component
public class ServiceGetTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceGetTask.class);

    @Resource
    private DiscoveryClient client;

    @Scheduled(initialDelay = 1000, fixedDelay = 300 * 1000)
    public void scheduleServiceTask() throws IOException {
        List<String> serviceNameLists = client.getServices();
        LOGGER.info(serviceNameLists.toString());
        String url = "http://39.104.112.98:8848/nacos/v1/ns/instance/list?serviceName=";
        for(String name : serviceNameLists) {
            LOGGER.info(name);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(url + name);
            ResponseHandler<String> handler = httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = httpResponse.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException(
                            "Unexpected response status: " + status);
                }
            };
            String res = null;
            try {
                res = httpClient.execute(get, handler);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                httpClient.close();
            }
            if(res == null)
                throw new IOException("execute failed");
            LOGGER.info(res);
            JSONObject jsonRes = JSON.parseObject(res);
            List<JSONObject> hotsLists = JSON.parseArray(jsonRes.getString("hosts")).toJavaList(JSONObject.class);
            LOGGER.info(jsonRes.getString("hosts"));
            LOGGER.info(hotsLists.get(0).getString("metadata"));
        }
    }
}
