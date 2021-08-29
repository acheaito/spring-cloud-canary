package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionParserTest {

    @Test
    public void throwsExceptionForBlankInput() {
        assertThrows(IllegalArgumentException.class, () -> new ConditionParser(null));
        assertThrows(IllegalArgumentException.class, () -> new ConditionParser(""));
    }

    @Test
    public void throwsMalformedConditionExceptionWhenConditionIsMalformed() {
        assertThrows(ConditionParser.MalformedConditionException.class, () -> new ConditionParser("endsWith("));
        assertThrows(ConditionParser.MalformedConditionException.class, () -> new ConditionParser("endsWith"));
        assertThrows(ConditionParser.MalformedConditionException.class, () -> new ConditionParser("endsWith(23 22"));
    }

    @Test
    public void throwsExceptionWhenOperationIsUnknown() {
        assertThrows(ConditionParser.UnknownOperation.class, () -> new ConditionParser("something()"));
    }

    @Test
    public void parsesOperationCorrectlyFromCondition() {
        assertEquals(new Operation(OperationName.ENDS_WITH, false), new ConditionParser("endsWith(ha ha)").getOperation());
    }

    @Test
    public void noArgumentsReturnsEmptyList() {
        assertArrayEquals(new String[]{},
                new ConditionParser("endsWith()")
                        .getArguments().toArray(new String[0]));

    }

    @Test
    public void ignoresExtraWhiteSpaceAroundArguments() {
        assertArrayEquals(new String[]{"23"},
                new ConditionParser("endsWith(  23   )")
                        .getArguments().toArray(new String[0]));
        assertArrayEquals(new String[]{"23", "ha"},
                new ConditionParser("endsWith(  23  ha   )")
                        .getArguments().toArray(new String[0]));
    }

    @Test
    public void parsesSpaceSeperatedArgumentsToListOfStrings() {
        assertArrayEquals(new String[]{"23"},
                new ConditionParser("endsWith(23)")
                        .getArguments().toArray(new String[0]));
        assertArrayEquals(new String[]{"23", "ha"},
                new ConditionParser("endsWith(23 ha)")
                        .getArguments().toArray(new String[0]));
        assertArrayEquals(new String[]{"23", "ha", "5"},
                new ConditionParser("endsWith(23 ha 5)")
                        .getArguments().toArray(new String[0]));
    }

    @Test
    public void isNegatedIsFalseWhenOperationIsNotNegated() {
        ConditionParser parser = new ConditionParser("endsWith(1 2)");
        assertFalse(parser.getOperation().isNegated());
    }

    @Test
    public void parsesOperationWhenConditionStartsWithExclamationMark() {
        ConditionParser parser = new ConditionParser("!endsWith(1 2)");
        assertEquals(OperationName.ENDS_WITH, parser.getOperation().getName());
    }

    @Test
    public void negatesOperationWhenConditionStartsWithExclamationMark() {
        ConditionParser parser = new ConditionParser("!endsWith(1 2)");
        assertTrue(parser.getOperation().isNegated());
    }

}
