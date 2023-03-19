package com.example.msversiongatewaycontroller.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.msversiongatewaycontroller.common.RemoveDuplication;
import com.example.msversiongatewaycontroller.entity.*;
import com.example.msversiongatewaycontroller.handler.GatewayServiceHandler;
import com.example.msversiongatewaycontroller.service.MServiceInterfaceService;
import com.example.msversiongatewaycontroller.service.MServiceService;
import com.example.msversiongatewaycontroller.service.MServiceVersionService;
import com.example.msversiongatewaycontroller.service.SysRouteConfService;
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
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.io.*;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author pivot
 */
@Api(tags = "接口操作")
@RestController
@RequestMapping("/api/interfaces")
public class ParseInterfaceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseInterfaceController.class);

    @Resource
    MServiceService mServiceService;

    @Resource
    private GatewayServiceHandler serviceHandler;

    @Resource
    MServiceVersionService mServiceVersionService;

    @Resource
    SysRouteConfService routeConfService;

    @Resource
    MServiceInterfaceService mServiceInterfaceService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @ApiOperation(value = "解析地址", notes = "解析地址接口")
    @PostMapping("/parse/url")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", paramType = "query", name = "url", value = "接口文档地址", required = true)
    })
    public Mono<Result> parseInterfacesFromUrl(@RequestBody String url) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String docs = Objects.requireNonNull(response.body()).string();
        parseToDataBase(docs);
        return Mono.just(ResultUtils.success());
    }

    //    @ApiOperation(value = "解析文件", notes = "解析文件接口")
//    @RequestMapping(value = "/parse/upload", method = RequestMethod.POST, produces = "multipart/form-data")
//    @ApiImplicitParams({
    @PostMapping(value = "/parse/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//            @ApiImplicitParam(dataType = "File", paramType = "query", name = "files", value = "接口文档", required = true)
//    })
    public Mono<Result> parseInterfacesFromFile(@RequestPart("file") FilePart file) throws IOException {
//        for (file : fileList) {
        String fileName = file.filename();
        System.out.println(fileName);
        ApplicationHome home = new ApplicationHome(this.getClass());
        Path tempFile = Files.createTempFile("temp", fileName);
        AsynchronousFileChannel channel =
                AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
        DataBufferUtils.write(file.content(), channel, 0)
                .publishOn(Schedulers.boundedElastic())
                .doOnComplete(() -> {
                    InputStream inputStream = null;
                    try {
                        LOGGER.info(tempFile.toFile().getPath());
                        inputStream = Files.newInputStream(new File(tempFile.toFile().getPath()).toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Yaml yaml = new Yaml();
                    String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                    String s = null;
                    try {
                        if ("yml".equals(fileType)) {
                            Object result = yaml.load(new FileInputStream(tempFile.toFile()));
                            s = JSON.parseObject(JSON.toJSONString(result)).toJSONString();
                        } else if ("json".equals(fileType)) {
                            s = new String(Files.readAllBytes(tempFile));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        parseToDataBase(s);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();

//        }


        return Mono.just(ResultUtils.success());
    }

    private void parseToDataBase(String docs) throws JsonProcessingException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(docs);
        boolean flag = node.has("swagger");
        JsonNode info = node.get("info");
        MService service = new MService();
        service.setmServiceName(info.get("title").asText());
        LOGGER.info("Service name is " + service.getmServiceName());
        String prefixRoute = "/" + service.getmServiceName() + "/{version:v[0-9]+\\.[0-9]+\\.[0-9]+}";
        service = mServiceService.selectByServiceName(service);
        if (service == null) {
            return;
        }
        int serviceId = service.getServiceId();
        String[] versions = info.get("version").asText().split("\\.");
        MServiceVersion serviceVersion = new MServiceVersion(serviceId, Integer.parseInt(versions[0]),
                Integer.parseInt(versions[1]), Integer.parseInt(versions[2]));
        serviceVersion = mServiceVersionService.selectByVersionAndService(serviceVersion);
        if (serviceVersion == null) {
            return;
        }
        int versionId = serviceVersion.getVersionId();
        LOGGER.info("获取到的version id为：" + versionId);
        JsonNode paths = node.get("paths");
        Iterator<String> keys = paths.fieldNames();
        StringBuilder res = new StringBuilder();
        while (keys.hasNext()) {
            String path = keys.next();
            JsonNode pathNode = paths.get(path);
            if (path.matches(".*/\\{.*}/.*") || path.matches(".*/\\{.*}")) {
                res.append(prefixRoute).append(path).append(", ");
            }
            LOGGER.info("解析到的path路径为： " + path);
            Iterator<String> methods = paths.get(path).fieldNames();
            while (methods.hasNext()) {
                MServiceInterface serviceInterface = new MServiceInterface();
                serviceInterface.setApi(path);
                serviceInterface.setVersionId(versionId);
                String method = methods.next();
                serviceInterface.setRequestType(method);
                LOGGER.info("解析到的method为：" + method);
                if (!flag) {
                    LOGGER.info("解析到的method向前兼容为： " +
                            pathNode.get(method).get("extensions").get("x-forward-compatible-marker").asText());
                    serviceInterface.setMarker(Integer.parseInt(
                            pathNode.get(method).get("extensions").get("x-forward-compatible-marker").asText()
                    ));
                } else {
                    LOGGER.info("解析到的method向前兼容为： " +
                            pathNode.get(method).get("x-forward-compatible-marker").asText());
                    serviceInterface.setMarker(Integer.parseInt(
                            pathNode.get(method).get("x-forward-compatible-marker").asText()
                    ));
                }
                serviceInterface.setDescription(
                        pathNode.get(method).get("summary").asText());
                MServiceInterface tempServiceInterface =
                        mServiceInterfaceService.selectInterfaceByMethodAndApi(serviceInterface);
                if (tempServiceInterface == null) {
                    mServiceInterfaceService.saveInterface(serviceInterface);
                } else {
                    serviceInterface.setId(tempServiceInterface.getId());
                    mServiceInterfaceService.updateById(serviceInterface);
                }
            }
        }
        SysRouteConf sysRouteConf = new SysRouteConf();
        sysRouteConf.setRouteId(service.getmServiceName());
        sysRouteConf.setUri("lb://" + sysRouteConf.getRouteId());
        sysRouteConf.setOrders(3);
        sysRouteConf.setFilters(null);
        SysRouteConf temp = new SysRouteConf();
        temp.setRouteId(sysRouteConf.getRouteId());
        temp = routeConfService.getOne(new QueryWrapper<>(temp));
        if (temp == null) {
            res.append(prefixRoute).append("/**");
            sysRouteConf.setPredicates("Path=" + res);
            routeConfService.add(sysRouteConf);
            serviceHandler.saveRoute(sysRouteConf);
        } else {
            sysRouteConf.setId(temp.getId());
            sysRouteConf.setPredicates("Path=" + new RemoveDuplication().mergePrefix(res + temp.getPredicates().substring(5)));
            routeConfService.update(sysRouteConf);
            serviceHandler.updateRoute(sysRouteConf);
        }
        Thread.sleep(1000);
    }
}
