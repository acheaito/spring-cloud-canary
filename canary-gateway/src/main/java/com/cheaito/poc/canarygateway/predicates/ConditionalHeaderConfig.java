package com.cheaito.poc.canarygateway.predicates;

import org.springframework.core.style.ToStringCreator;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public class ConditionalHeaderConfig {
    @NotEmpty
    private String header;
    @NotEmpty
    private String condition;

    public ConditionalHeaderConfig(String header, String condition) {
        this.header = header;
        this.condition = condition;
    }

    public String getHeader() {
        return header;
    }

    public String getCondition() {
        return condition;
    }

    public ConditionalHeaderConfig setHeader(String header) {
        this.header = header;
        return this;
    }

    public ConditionalHeaderConfig setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("header", header)
                .append("condition", condition)
                .toString();
    }
}
