package com.cheaito.poc.canarygateway.predicates;

public class ConditionParserFactory {
    public ConditionParser create(String condition) {
        return new ConditionParser(condition);
    }
}
