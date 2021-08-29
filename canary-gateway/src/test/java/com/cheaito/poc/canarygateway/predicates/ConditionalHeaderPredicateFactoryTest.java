package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConditionalHeaderPredicateFactoryTest {
    @Mock
    private ConditionParser parser;

    @Test
    public void invokesParserGetOperation() {
        verify(parser).getOperation();
        new ConditionalHeaderPredicateFactory(parser)
                .apply(new ConditionalHeaderConfig("x-header", "startsWith(1)"));
    }

}
