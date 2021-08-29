package com.cheaito.poc.canarygateway.predicates;

import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

public class ConditionalHeaderPredicateFactory implements RoutePredicateFactory<ConditionalHeaderConfig> {

    private final ConditionParser parser;

    public ConditionalHeaderPredicateFactory(ConditionParser parser) {
        this.parser = parser;
    }

    @Override
    public Predicate<ServerWebExchange> apply(ConditionalHeaderConfig config) {
        return (GatewayPredicate) serverWebExchange -> {
            return false;
        };
    }

    @Override
    public Class<ConditionalHeaderConfig> getConfigClass() {
        return ConditionalHeaderConfig.class;
    }
}
