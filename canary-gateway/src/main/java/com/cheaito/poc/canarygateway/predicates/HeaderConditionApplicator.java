package com.cheaito.poc.canarygateway.predicates;

import java.util.List;

public class HeaderConditionApplicator {
    private String operation;
    public HeaderConditionApplicator(String headerValue, String operation, List<String> arguments) {
    validateOperation();
    }

    private void validateOperation() {
        throw new IllegalArgumentException("Operation cannot be blank");
    }
}
