package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OperationNameTest {

    @Test
    public void returnsOpNameForOperation() {
        assertEquals("endsWith", OperationName.ENDS_WITH.getOpName());
    }

    @Test
    public void returnsEnumNameForOpName() {
        assertEquals(OperationName.ENDS_WITH, OperationName.from("endsWith"));
    }

    @Test
    public void returnsTrueIfKeyIsValid() {
        assertTrue(OperationName.isValid("endsWith"));
    }

    @Test
    public void returnsFalseIfKeyIsInvalid() {
        assertFalse(OperationName.isValid("endsWith111"));
    }

    @Test
    public void hasObjectEquality() {
        assertEquals(new Operation(OperationName.ENDS_WITH, true),
                new Operation(OperationName.ENDS_WITH, true));
    }

    @Test
    public void canBeUsedAsMapKey() {
        Map<Operation, String> map = new HashMap<>();
        map.put(new Operation(OperationName.ENDS_WITH, true), "hello");
        assertEquals("hello",
                map.get(new Operation(OperationName.ENDS_WITH, true)));
    }
}
