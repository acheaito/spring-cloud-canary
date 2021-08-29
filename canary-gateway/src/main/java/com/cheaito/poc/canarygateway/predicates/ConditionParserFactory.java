package com.cheaito.poc.canarygateway.predicates;

import org.springframework.stereotype.Component;

@Component
public class ConditionParserFactory {
    public ConditionParser create(String condition) {
        return new ConditionParser(condition);
    }
}
