package com.cheaito.poc.canarygateway.predicates;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HeaderConditionParser {
    private static final List<String> DEFINED_OPERATIONS = Arrays.asList("endsWith", "notEndsWith");
    private static final String ARGUMENT_SEPARATOR = " ";
    public static final Pattern ARGUMENT_PATTERN = Pattern.compile("(!?)([\\w].*)\\((.*)\\)");
    private final String header;
    private String operation;
    private List<String> arguments;
    private Matcher matcher;

    public HeaderConditionParser(String header, String condition) {
        validateInputNotBlank(header, condition);
        createConditionMatcher(condition);
        parseOperation();
        parseArguments();
        this.header = header;
    }

    private void parseArguments() {
        String argString = matcher.group(3).trim();
        this.arguments = Arrays.stream(argString.split(ARGUMENT_SEPARATOR))
                .map(String::trim)
                .filter(s -> !Strings.isBlank(s))
                .collect(Collectors.toList());
    }

    private void parseOperation() {
        this.operation = matcher.group(2);
        if (isNegated())
            negateOperation();
        if (!DEFINED_OPERATIONS.contains(operation))
            throw new UnsupportedOperationException("Requested operation is not valid: " + operation);
    }

    private boolean isNegated() {
        return "!".equals(matcher.group(1));
    }

    private void negateOperation() {
        operation = "not" + operation.substring(0,1).toUpperCase() + operation.substring(1);
    }

    private void createConditionMatcher(String condition) {
        matcher = ARGUMENT_PATTERN.matcher(condition);
        if (!matcher.matches())
            throw new MalformedConditionException("Malformed condition: " + condition);
    }

    private void validateInputNotBlank(String header, String condition) {
        if(Strings.isBlank(header))
            throw new IllegalArgumentException("header cannot be blank");
        if(Strings.isBlank(condition))
            throw new IllegalArgumentException("condition cannot be blank");
    }

    public String getHeader() {
        return header;
    }

    public String getOperation() {
        return operation;
    }

    public List<String> getArguments() {
        return this.arguments;
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
