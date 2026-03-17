package com.eazybytes.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EazybankRouteLocator {
    @Bean
    public RouteLocator eazybankRouteConfig(RouteLocatorBuilder builder){
        return builder.routes()
                .route( r -> r
                        .path("/eazybank/accounts/**")
                        .filters(f -> f.rewritePath("/eazybank/accounts/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACCOUNTS"))
                .route( r -> r
                        .path("/eazybank/loans/**")
                        .filters(f -> f.rewritePath("/eazybank/loans/(?<segment>.*)", "/${segment}"))
                        .uri("lb://LOANS"))
                .route( r -> r
                        .path("/eazybank/cards/**")
                        .filters(f -> f.rewritePath("/eazybank/cards/(?<segment>.*)", "/${segment}"))
                        .uri("lb://CARDS")).build();
    }
}
