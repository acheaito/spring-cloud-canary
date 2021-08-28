package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderConditionParserTest {

    @Test
    public void throwsExceptionForBlankInput() {
        assertThrows(IllegalArgumentException.class, () -> new HeaderConditionParser(null, null));
        assertThrows(IllegalArgumentException.class, () -> new HeaderConditionParser("x-fake-header", null));
        assertThrows(IllegalArgumentException.class, () -> new HeaderConditionParser(null, "endsWith(1)"));
        assertThrows(IllegalArgumentException.class, () -> new HeaderConditionParser("", ""));
        assertThrows(IllegalArgumentException.class, () -> new HeaderConditionParser("x-fake-header", ""));
        assertThrows(IllegalArgumentException.class, () -> new HeaderConditionParser("", "endsWith(1)"));
    }

    @Test
    public void parsesHeaderCorrectly() {
        assertEquals("x-fake-header", new HeaderConditionParser("x-fake-header", "endsWith(1)").getHeader());
        assertEquals("x-fake-header2", new HeaderConditionParser("x-fake-header2", "endsWith(1)").getHeader());
    }

    @Test
    public void throwsMalformedConditionExceptionWhenConditionIsMalformed() {
        assertThrows(HeaderConditionParser.MalformedConditionException.class, () -> new HeaderConditionParser("x-fake-header", "endsWith("));
        assertThrows(HeaderConditionParser.MalformedConditionException.class, () -> new HeaderConditionParser("x-fake-header", "endsWith"));
        assertThrows(HeaderConditionParser.MalformedConditionException.class, () -> new HeaderConditionParser("x-fake-header", "endsWith(23 22"));
    }

    @Test
    public void throwsUnsupportedOperationExceptionWhenOperationIsUnknown() {
        assertThrows(HeaderConditionParser.UnsupportedOperationException.class, () -> new HeaderConditionParser("x-fake-header", "something()"));
    }

    @Test
    public void parsesOperationCorrectlyFromCondition() {
        assertEquals("endsWith", new HeaderConditionParser("x-fake-header", "endsWith(ha ha)").getOperation());
    }

    @Test
    public void noArgumentsReturnsEmptyList() {
        assertArrayEquals(new String[]{},
                new HeaderConditionParser("x-fake-header", "endsWith()")
                        .getArguments().toArray(String[]::new));

    }

    @Test
    public void ignoresExtraWhiteSpaceAroundArguments() {
        assertArrayEquals(new String[]{"23"},
                new HeaderConditionParser("x-fake-header", "endsWith(  23   )")
                        .getArguments().toArray(String[]::new));
        assertArrayEquals(new String[]{"23", "ha"},
                new HeaderConditionParser("x-fake-header", "endsWith(  23  ha   )")
                        .getArguments().toArray(String[]::new));
    }

    @Test
    public void parsesSpaceSeperatedArgumentsToListOfStrings() {
        assertArrayEquals(new String[]{"23"},
                new HeaderConditionParser("x-fake-header", "endsWith(23)")
                        .getArguments().toArray(String[]::new));
        assertArrayEquals(new String[]{"23", "ha"},
                new HeaderConditionParser("x-fake-header", "endsWith(23 ha)")
                        .getArguments().toArray(String[]::new));
        assertArrayEquals(new String[]{"23", "ha", "5"},
                new HeaderConditionParser("x-fake-header", "endsWith(23 ha 5)")
                        .getArguments().toArray(String[]::new));
    }

    @Test
    public void negatesOperationWhenConditionStartsWithExclamationMark() {
        assertEquals("notEndsWith", new HeaderConditionParser("x-fake-header", "!endsWith(1 2)").getOperation());
    }

}
