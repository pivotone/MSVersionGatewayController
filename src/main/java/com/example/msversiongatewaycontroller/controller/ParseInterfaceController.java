package com.example.msversiongatewaycontroller.controller;

import com.example.msversiongatewaycontroller.entity.MService;
import com.example.msversiongatewaycontroller.entity.MServiceInterface;
import com.example.msversiongatewaycontroller.entity.MServiceVersion;
import com.example.msversiongatewaycontroller.entity.Result;
import com.example.msversiongatewaycontroller.service.MServiceInterfaceService;
import com.example.msversiongatewaycontroller.service.MServiceService;
import com.example.msversiongatewaycontroller.service.MServiceVersionService;
import com.example.msversiongatewaycontroller.utils.ResultUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

@Api(tags = "接口操作")
@RestController
@RequestMapping("/api/interfaces")
public class ParseInterfaceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseInterfaceController.class);

    @Resource
    MServiceService mServiceService;

    @Resource
    MServiceVersionService mServiceVersionService;

    @Resource
    MServiceInterfaceService mServiceInterfaceService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @ApiOperation(value = "解析", notes = "解析接口")
    @PostMapping("/parse")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", paramType = "query", name = "url", value = "接口文档地址", required = true)
    })
    public Result parseInterfaces(@RequestBody String url) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String docs = Objects.requireNonNull(response.body()).string();
        parseToDataBase(docs);
        return ResultUtils.success();
    }

    private void parseToDataBase(String docs) throws JsonProcessingException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(docs);
        JsonNode info = node.get("info");
        MService service = new MService();
        service.setmServiceName(info.get("title").asText());
        LOGGER.info("Service name is " + service.getmServiceName());
        service = mServiceService.selectByServiceName(service);
        if(service == null) {
            return;
        }
        int serviceId = service.getServiceId();
        String[] versions = info.get("version").asText().split("\\.");
        MServiceVersion serviceVersion = new MServiceVersion(serviceId, Integer.parseInt(versions[0]),
                Integer.parseInt(versions[1]), Integer.parseInt(versions[2]));
        serviceVersion = mServiceVersionService.selectByVersionAndService(serviceVersion);
        if(serviceVersion == null){
            return;
        }
        int versionId = serviceVersion.getVersionId();
        LOGGER.info("获取到的version id为：" + versionId);
        JsonNode paths = node.get("paths");
        Iterator<String> keys = paths.fieldNames();
        while(keys.hasNext()) {
            threadPoolTaskExecutor.submit(new Thread(() -> {
                String path = keys.next();
                JsonNode pathNode = paths.get(path);
                LOGGER.info("解析到的path路径为： " + path);
                Iterator<String> methods = paths.get(path).fieldNames();
                while(methods.hasNext()) {
                    MServiceInterface serviceInterface = new MServiceInterface();
                    serviceInterface.setApi(path);
                    serviceInterface.setVersionId(versionId);
                    String method = methods.next();
                    LOGGER.info("解析到的method为：" + method);
                    LOGGER.info("解析到的method向前兼容为： " +
                            pathNode.get(method).get("extensions").get("x-forward-compatible-marker").asText());
                    serviceInterface.setRequestType(method);
                    serviceInterface.setMarker(Integer.parseInt(
                            pathNode.get(method).get("extensions").get("x-forward-compatible-marker").asText()
                    ));
                    serviceInterface.setDescription(
                            pathNode.get(method).get("summary").asText());
                    MServiceInterface tempServiceInterface =
                            mServiceInterfaceService.selectInterfaceByMethodAndApi(serviceInterface);
                    if(tempServiceInterface == null) {
                        mServiceInterfaceService.saveInterface(serviceInterface);
                    }
                }
            }));
        }
        Thread.sleep(1000);
    }
}
