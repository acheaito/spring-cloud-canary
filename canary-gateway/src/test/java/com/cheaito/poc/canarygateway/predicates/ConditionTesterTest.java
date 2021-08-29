package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionTesterTest {
    private static final Operation ENDS_WITH = new Operation(OperationName.ENDS_WITH, false);
    private static final Operation NEGATED_ENDS_WITH = new Operation(OperationName.ENDS_WITH, true);

    @Test
    public void throwsExceptionOnBlankOperation() {
        assertThrows(IllegalArgumentException.class, () -> new ConditionTester("", null, Collections.singletonList("")));
    }

    @Test
    public void returnsFalseWhenValueIsBlank() {
        assertFalse(new ConditionTester("", ENDS_WITH, null).test());
        assertFalse(new ConditionTester(null, ENDS_WITH, null).test());
        assertFalse(new ConditionTester("   ", ENDS_WITH, null).test());
    }

    @Test
    public void returnsTrueWhenValueEndsWithSingleDesiredInput() {
        assertTrue(new ConditionTester("123abc1", ENDS_WITH, Collections.singletonList("1")).test());
        assertTrue(new ConditionTester("123177a", ENDS_WITH, Arrays.asList("a", "c")).test());
    }

    @Test
    public void returnsTrueWhenValueEndsWithAnyDesiredInput() {
        assertTrue(new ConditionTester("123abc1", ENDS_WITH, Arrays.asList("1","a")).test());
        assertTrue(new ConditionTester("123177a", ENDS_WITH, Arrays.asList("c", "a")).test());
        assertTrue(new ConditionTester("123177a", ENDS_WITH, Arrays.asList("c", "a", "d")).test());
        assertTrue(new ConditionTester("123177a", ENDS_WITH, Arrays.asList("c", "d", "a")).test());
    }

    @Test
    public void returnsFalseWhenValueDoesNotEndWithDesiredInput() {
        assertFalse(new ConditionTester("123abc1", ENDS_WITH, Collections.singletonList("2")).test());
        assertFalse(new ConditionTester("123abc1", ENDS_WITH, Arrays.asList("2","5","7")).test());
    }
    
    @Test
    public void invertsConditionIfNegatedFlagIsSet() {
        assertFalse(new ConditionTester("123abc1", NEGATED_ENDS_WITH, Collections.singletonList("1")).test());
        assertFalse(new ConditionTester("123abc1", NEGATED_ENDS_WITH, Arrays.asList("a", "2", "1")).test());
        assertTrue(new ConditionTester("123abcf", NEGATED_ENDS_WITH, Arrays.asList("a", "2")).test());
    }
}
