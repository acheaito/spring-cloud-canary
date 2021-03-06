package com.cheaito.canarygateway.predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_PREDICATE_ROUTE_ATTR;

@Component
public class ConditionalHeaderPredicateFactory implements RoutePredicateFactory<ConditionalHeaderConfig> {
    private static final Logger log = LoggerFactory.getLogger(ConditionalHeaderPredicateFactory.class);
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
            boolean decision = tester.test();
            logDecision(exchange, decision, config.getHeader(),
                    valueToTest, parser.getOperation(), parser.getArguments());
            return decision;
        };
    }

    private void logDecision(ServerWebExchange exchange, boolean decision, String header,
                             String valueToTest, Operation operation, List<String> arguments) {
        log.trace("route {} {} followed. Operation {}{}{} was applied to header {} with value {}",
                exchange.getAttribute(GATEWAY_PREDICATE_ROUTE_ATTR),
                (decision ? " was" : " WAS NOT"),
                (operation.isNegated() ? "!" : ""),
                operation.getName(),
                arguments.toString(),
                header,
                valueToTest);
    }

    @Override
    public Class<ConditionalHeaderConfig> getConfigClass() {
        return ConditionalHeaderConfig.class;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("header", "condition");
    }
}
