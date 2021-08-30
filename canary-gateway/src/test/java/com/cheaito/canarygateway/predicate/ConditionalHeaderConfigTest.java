package com.cheaito.canarygateway.predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConditionalHeaderConfigTest {

    private ConditionalHeaderConfig config;

    @BeforeEach
    void setUp() {
        config = new ConditionalHeaderConfig("x-account-key", "endsWith(1)");
    }

    @Test
    public void acceptsHeaderAndCondition() {
        assertEquals("x-account-key", config.getHeader());
        assertEquals("endsWith(1)", config.getCondition());
    }

    @Test
    public void settersUpdatesGetterValues() {
        config.setHeader("new-header");
        assertEquals("new-header", config.getHeader());
        config.setCondition("startsWith(1)");
        assertEquals("startsWith(1)", config.getCondition());
    }

    @Test
    public void settersReturnUpdatedObject() {
        ConditionalHeaderConfig newConfig = config.setHeader("new-header");
        assertEquals(newConfig, config.setHeader("new-header"));
        ConditionalHeaderConfig newConfig2 = config.setCondition("startsWith(1)");
        assertEquals(newConfig2, config.setCondition("startsWith(1)"));
    }

    @Test
    public void overridesToString() {
        String result = config.toString();
        assertTrue(result.endsWith("header = 'x-account-key', condition = 'endsWith(1)']"));
    }
}
