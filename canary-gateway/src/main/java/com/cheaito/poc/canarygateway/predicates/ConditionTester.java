package com.cheaito.poc.canarygateway.predicates;

import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Objects;

public class ConditionTester {
    private Operation operation;
    private String valueToTest;
    private List<String> arguments;

    public ConditionTester(String valueToTest, Operation operation, List<String> arguments) {
        this.valueToTest = valueToTest;
        this.operation = operation;
        this.arguments = arguments;
        validateOperation();
    }

    private void validateOperation() {
        if (Objects.isNull(operation))
            throw new IllegalArgumentException("Operation cannot be blank");
    }

    public boolean test() {
        if (Strings.isBlank(valueToTest))
            return false;
        if (operation.getName() == OperationName.ENDS_WITH)
            return testEndsWith();
        throw new OperationNotImplemented("Operation is not yet implemented: " + operation.getName());
    }

    private boolean testEndsWith() {
        boolean result = checkIfInputEndsWithArguments();
        if (operation.isNegated())
            return !result;
        return result;
    }

    private boolean checkIfInputEndsWithArguments() {
        for(String argument : arguments) {
            if (valueToTest.endsWith(argument)) {
                return true;
            }
        }
        return false;
    }

    public static class OperationNotImplemented extends RuntimeException{
        public OperationNotImplemented(String message) {
            super(message);
        }
    }
}
