package com.cheaito.canarygateway.predicate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum OperationName {
    ENDS_WITH("endsWith"),
    STARTS_WITH("startsWith");
    private final String opName;


    private static final Map<String, OperationName> lookup = new HashMap<>();
    static {
        Arrays.stream(OperationName.values())
                .forEach(v -> lookup.put(v.getOpName(), v));
    }

    OperationName(String opName) {
        this.opName = opName;
    }


    public static OperationName from(String opName) {
        return lookup.get(opName);
    }

    public static boolean isValid(String opName) {
        return lookup.containsKey(opName);
    }

    public String getOpName() {
        return opName;
    }
}
