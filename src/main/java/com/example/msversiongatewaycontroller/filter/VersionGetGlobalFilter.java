package com.example.msversiongatewaycontroller.filter;

import com.example.msversiongatewaycontroller.rule.VersionLoadBalancerRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class VersionGetGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionLoadBalancerRule.class);
    public static String VERSION = "latest";
    public static String SERVICE_NAME = "service";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();
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
        }
        uri = uri.replace("/" + VERSION, "");
        uri = uri.replace("/" + SERVICE_NAME, "");

        LOGGER.info("new create uri is " + uri);

        ServerHttpRequest request = exchange.getRequest().mutate().path(uri).build();
        exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, request.getURI());



        return chain.filter(exchange.mutate().request(request).build());

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
