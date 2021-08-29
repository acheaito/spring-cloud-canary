package com.cheaito.poc.canarygateway.predicates;

import org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

public class ConditionalHeaderPredicateFactory implements RoutePredicateFactory<ConditionalHeaderConfig> {

    private final ConditionParserFactory parserFactory;
    private final ConditionTesterFactory testerFactory;

    public ConditionalHeaderPredicateFactory(ConditionParserFactory parserFactory, ConditionTesterFactory testerFactory) {
        this.parserFactory = parserFactory;
        this.testerFactory = testerFactory;
    }

    @Override
    public Predicate<ServerWebExchange> apply(ConditionalHeaderConfig config) {
        ConditionParser parser = parserFactory.create(config.getCondition());

        return exchange -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String valueToTest = headers.getFirst(config.getHeader());
            ConditionTester tester = testerFactory.create(valueToTest,
                    parser.getOperation(),
                    parser.getArguments());
            return tester.test();
        };
    }

    @Override
    public Class<ConditionalHeaderConfig> getConfigClass() {
        return ConditionalHeaderConfig.class;
    }
}
