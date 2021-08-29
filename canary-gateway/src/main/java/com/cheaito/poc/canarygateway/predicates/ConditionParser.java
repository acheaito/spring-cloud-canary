package com.cheaito.poc.canarygateway.predicates;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConditionParser {
    private static final String ARGUMENT_SEPARATOR = " ";
    public static final Pattern ARGUMENT_PATTERN = Pattern.compile("(!?)([\\w].*)\\((.*)\\)");
    private Operation operation;
    private List<String> arguments;
    private Matcher matcher;

    public ConditionParser(String condition) {
        validateInputNotBlank(condition);
        createConditionMatcher(condition);
        parseOperation();
        parseArguments();
    }

    public Operation getOperation() {
        return operation;
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    private void parseArguments() {
        String argString = matcher.group(3).trim();
        this.arguments = Arrays.stream(argString.split(ARGUMENT_SEPARATOR))
                .map(String::trim)
                .filter(s -> !Strings.isBlank(s))
                .collect(Collectors.toList());
    }

    private void parseOperation() {
        String opName = matcher.group(2);
        if (unknownOperation(opName))
            throw new UnsupportedOperationException("Requested operation is not valid: " + operation);

        OperationName operationName = OperationName.from(opName);
        boolean isNegated = "!".equals(matcher.group(1));
        operation = new Operation(operationName, isNegated);
    }

    private boolean unknownOperation(String opName) {
        return !OperationName.isValid(opName);
    }

    private void createConditionMatcher(String condition) {
        matcher = ARGUMENT_PATTERN.matcher(condition);
        if (!matcher.matches())
            throw new MalformedConditionException("Malformed condition: " + condition);
    }

    private void validateInputNotBlank(String condition) {
        if(Strings.isBlank(condition))
            throw new IllegalArgumentException("condition cannot be blank");
    }

    public static class UnsupportedOperationException extends RuntimeException{
        public UnsupportedOperationException(String message) {
            super(message);
        }
    }

    public static class MalformedConditionException extends RuntimeException{
        public MalformedConditionException(String message) {
            super(message);
        }
    }
}
