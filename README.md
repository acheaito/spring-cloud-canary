# spring-cloud-canary
A Spring Cloud Gateway canary configuration example with 90/10 distribution

# Running the example
1. Run CreditCheckApiApplication.java
2. Run CanaryGatewayApplication.java
3. GET http://localhost:8080/creditcheck

90% of the requests should go to legacy API (response is LEGACY), the rest should go to modern API (response is MODERN)

# Changing the distribution
Update the Weight configuration in canary-gateway/application.yaml to change the request distribution
