package com.cheaito.poc.canarygateway.predicates;

import java.util.List;

public class ConditionTesterFactory {
    public ConditionTester create(String valueToTest, Operation operation, List<String> arguments) {
        return new ConditionTester(valueToTest, operation, arguments);
    }
}
