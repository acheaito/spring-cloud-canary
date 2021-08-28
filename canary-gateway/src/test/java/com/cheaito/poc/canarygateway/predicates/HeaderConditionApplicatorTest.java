package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderConditionApplicatorTest {
    @Test
    public void throwsExceptionOnBlankOperation() {
        assertThrows(IllegalArgumentException.class, () -> new HeaderConditionApplicator("", null, Arrays.asList("")));
    }
}
