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

import static com.example.msversiongatewaycontroller.filter.VersionGetGlobalFilter.VERSION;

@Component
public class CreateNewRouteGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionLoadBalancerRule.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String majorVersion = VERSION.substring(0, VERSION.indexOf('.'));
        LOGGER.info("major version is " + majorVersion);
        route = Route.async()
                .filters(route.getFilters())
                .asyncPredicate(route.getPredicate())
                .id(route.getId())
                .uri(route.getUri().toString() + "-" + majorVersion)
                .order(route.getOrder())
                .build();
        exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR, route);
        ServerHttpRequest request = exchange.getRequest().mutate().uri(exchange.getRequest().getURI()).build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
