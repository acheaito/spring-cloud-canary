package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConditionParserFactoryTest {
    @Test
    public void createsConditionParserInstance() {
        ConditionParser parser = new ConditionParserFactory().create("endsWith(1)");
        assertNotNull(parser);
    }
}
