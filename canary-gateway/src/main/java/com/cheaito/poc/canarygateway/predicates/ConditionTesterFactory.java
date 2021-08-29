package com.cheaito.poc.canarygateway.predicates;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConditionTesterFactory {
    public ConditionTester create(String valueToTest, Operation operation, List<String> arguments) {
        return new ConditionTester(valueToTest, operation, arguments);
    }
}
