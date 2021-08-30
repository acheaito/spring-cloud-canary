package com.cheaito.canarygateway.predicate;

import org.springframework.stereotype.Component;

@Component
public class ConditionParserFactory {
    public ConditionParser create(String condition) {
        return new ConditionParser(condition);
    }
}
