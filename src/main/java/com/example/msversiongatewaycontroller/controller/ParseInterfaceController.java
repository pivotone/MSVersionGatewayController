package com.example.msversiongatewaycontroller.controller;

import com.example.msversiongatewaycontroller.entity.Result;
import com.example.msversiongatewaycontroller.utils.ResultUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@Api(tags = "接口操作")
@RestController
@RequestMapping("/api/interfaces")
public class ParseInterfaceController {

    @ApiOperation(value = "解析", notes = "解析接口")
    @PostMapping("/parse")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", paramType = "query", name = "url", value = "接口文档地址", required = true)
    })
    public Result parseInterfaces(@RequestBody String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(Objects.requireNonNull(response.body()).string());
        JsonNode paths = node.get("paths");
        return ResultUtils.success(paths);
    }
}
