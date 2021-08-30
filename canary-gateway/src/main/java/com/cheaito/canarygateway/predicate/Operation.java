package com.cheaito.canarygateway.predicate;

import java.util.Objects;

public class Operation {
    private final OperationName name;
    private final boolean negated;

    public Operation(OperationName name, boolean negated) {
        this.name = name;
        this.negated = negated;
    }

    public OperationName getName() {
        return name;
    }

    public boolean isNegated() {
        return negated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return negated == operation.negated && name == operation.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, negated);
    }
}
