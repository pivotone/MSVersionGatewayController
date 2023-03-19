package com.example.msversiongatewaycontroller.filter;

import com.example.msversiongatewaycontroller.common.VersionStringOp;
import com.example.msversiongatewaycontroller.rule.VersionLoadBalancerRule;
import com.example.msversiongatewaycontroller.service.VersionMarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author pivot
 * @since 22/10/27
 */
@Component
public class VersionGetGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionLoadBalancerRule.class);
    public static volatile String VERSION = "latest";
    public static volatile String SERVICE_NAME = "service";
    public static String api;
    public static String requestType;
    @Resource
    VersionMarkerService service;

    public static String[] intervals;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();
        Map<String, String> map = exchange.getAttribute(ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Object headInfo = exchange.getRequest().getHeaders().get("version");
        LOGGER.info("origin uri is " + uri);
        String regex = "v[0-9]\\.[0-9]\\.[0-9]";
        if(uri.matches(".*/" + regex + "/.*")){
            String[] patterns = uri.split("/");
            setServiceName(patterns[1]);
            for(String str : patterns) {
                if(str.matches(regex)) {
                    setVERSION(str);
                    break;
                }
            }
        } else if(headInfo != null){
            setVERSION(headInfo.toString());
        }
        uri = uri.replace("/" + SERVICE_NAME + "/" + VERSION, "");

        LOGGER.info("new create uri is " + uri);
        LOGGER.info(exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_PREDICATE_MATCHED_PATH_ATTR)
                .toString().replace("/" + SERVICE_NAME + "/{version:v[0-9]+.[0-9]+.[0-9]+}", ""));

        api = uri;
        String oriUrl = uri;

        if(map != null && !map.isEmpty() && map.size() > 1) {
            oriUrl = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_PREDICATE_MATCHED_PATH_ATTR)
                    .toString().replace("/" + SERVICE_NAME + "/{version:v[0-9]+\\.[0-9]+\\.[0-9]+}/", "/");
            int count = 0, pos = -1, temp = 0;
            for(int i = 0; i < oriUrl.length(); ++i)
                if(oriUrl.charAt(i) == '/')
                    count++;
            for(int i = 0; i < uri.length(); ++i) {
                if(uri.charAt(i) == '/') {
                    temp++;
                    if(temp == count) {
                        pos = i;
                        break;
                    }
                }
            }
            oriUrl = oriUrl.substring(0, oriUrl.length() - 3) + (pos >= 0 ? uri.substring(pos) : "");
        }

        requestType = Objects.requireNonNull(exchange.getRequest().getMethod()).toString().toLowerCase();

        intervals = getVersionInterval(oriUrl);

        ServerHttpRequest request = exchange.getRequest().mutate().path(uri).build();
        exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, request.getURI());



        return chain.filter(exchange.mutate().request(request).build());

    }

    public String[] getVersionInterval(String url) {
        Map<String, Object> params = new HashMap<>();
        VersionStringOp stringOp = new VersionStringOp();
        int[] versionArrays = stringOp.stringVersionToIntArray(VERSION.replace("v", ""));
        params.put("major", versionArrays[0]);
        params.put("minor", versionArrays[1]);
        params.put("patch", versionArrays[2]);
        params.put("url", url);
        params.put("requestType", requestType);
        service.callGetVersionInterval(params);
        String[] interval = new String[2];
        interval[0] = (String) params.get("leftVersion");
        interval[1] = (String) params.get("rightVersion");

        return interval;
    }

    private void setServiceName(String serviceName) {
        SERVICE_NAME = serviceName;
    }

    private void setVERSION(String version) {
        VERSION = version;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
