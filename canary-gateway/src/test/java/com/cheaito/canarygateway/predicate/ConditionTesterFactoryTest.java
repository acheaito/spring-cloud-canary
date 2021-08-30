package com.cheaito.canarygateway.predicate;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ConditionTesterFactoryTest {

    @Test
    public void createsConditionTesterInstance() {
        ConditionTesterFactory factory = new ConditionTesterFactory();
        ConditionTester tester = factory.create("something",
                new Operation(OperationName.ENDS_WITH, false)
                , Collections.emptyList());
        assertNotNull(tester);
    }

}
