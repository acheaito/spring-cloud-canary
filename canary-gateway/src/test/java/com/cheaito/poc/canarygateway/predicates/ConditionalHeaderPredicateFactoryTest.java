package com.cheaito.poc.canarygateway.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConditionalHeaderPredicateFactoryTest {
    public static final String X_ACCOUNT_KEY = "x-account-key";
    public static final String CONDITION = "endsWith(1)";
    public static final String ACCOUNT_KEY_VALUE = "0001112222";
    @Mock
    private ConditionParserFactory parserFactory;
    @Mock
    private ConditionParser parser;

    @Mock
    private ConditionTesterFactory testerFactory;
    @Mock
    private ConditionTester tester;
    @Mock
    private ServerWebExchange serverExchange;
    @Mock
    private ServerHttpRequest request;
    @Mock
    private HttpHeaders headers;

    @Captor
    private ArgumentCaptor<String> accountValueCaptor;
    @Captor
    private ArgumentCaptor<Operation> operationCaptor;
    @Captor
    private ArgumentCaptor<List<String>> listArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> conditionCaptor;

    private ConditionalHeaderConfig config;
    private final Operation ENDS_WITH_OPERATION = new Operation(OperationName.ENDS_WITH, false);
    private ConditionalHeaderPredicateFactory subject;

    @BeforeEach
    public void setUp() {
        config = new ConditionalHeaderConfig(X_ACCOUNT_KEY, CONDITION);
        subject = new ConditionalHeaderPredicateFactory(parserFactory, testerFactory);
    }

    private void setupMocks() {
        when(parserFactory.create(CONDITION))
                .thenReturn(parser);
        when(parser.getArguments()).thenReturn(Collections.emptyList());
        when(parser.getOperation()).thenReturn(ENDS_WITH_OPERATION);
        when(testerFactory.create(ACCOUNT_KEY_VALUE, ENDS_WITH_OPERATION, Collections.emptyList())).thenReturn(tester);
        when(serverExchange.getRequest()).thenReturn(request);
        when(request.getHeaders()).thenReturn(headers);
        when(headers.getFirst(X_ACCOUNT_KEY)).thenReturn(ACCOUNT_KEY_VALUE);
    }

    @Test
    public void invokesParserGetOperation() {
        setupMocks();
        subject
                .apply(config)
                .test(serverExchange);
        verify(parser, times(2)).getOperation();
    }

    @Test
    public void invokesFactoryCreateMethodForParser() {
        setupMocks();
        subject
                .apply(config)
                .test(serverExchange);
        verify(parserFactory).create(conditionCaptor.capture());
        assertEquals(CONDITION, conditionCaptor.getValue());
    }

    @Test
    public void invokesFactoryCreateMethodForTester() {
        setupMocks();
        subject
                .apply(config)
                .test(serverExchange);
        verify(testerFactory).create(accountValueCaptor.capture(),
                operationCaptor.capture(),
                listArgumentCaptor.capture());
        assertEquals(ACCOUNT_KEY_VALUE, accountValueCaptor.getValue());
        assertEquals(ENDS_WITH_OPERATION, operationCaptor.getValue());
        assertEquals(Collections.emptyList(), listArgumentCaptor.getValue());
    }

    @Test
    public void invokesConditionTesterTestMethod() {
        setupMocks();
        subject
                .apply(config)
                .test(serverExchange);
        verify(tester).test();
    }

    @Test
    public void returnsCorrectConfigClassType() {
        assertEquals(ConditionalHeaderConfig.class,
                subject.getConfigClass());
    }

    @Test
    public void returnsCorrectFieldsInOrder() {
        assertEquals(Arrays.asList("header", "condition"), subject.shortcutFieldOrder());
    }
}
