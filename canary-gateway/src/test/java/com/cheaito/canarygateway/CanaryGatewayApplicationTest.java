package com.cheaito.canarygateway;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class CanaryGatewayApplicationTest {

    @Test
    public void startsSpringApplication() {
        try(MockedStatic<SpringApplication> staticMock = mockStatic(SpringApplication.class)) {
            staticMock.when(() -> SpringApplication.run(CanaryGatewayApplication.class)).thenReturn(null);
            CanaryGatewayApplication.main(new String[]{});
            staticMock.verify(() -> SpringApplication.run(CanaryGatewayApplication.class));
        }
    }

}
