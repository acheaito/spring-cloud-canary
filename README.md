# spring-cloud-canary
This project has two examples of Spring Cloud Gateway canary configuration:
1. Percentage based routing (90/10 distribution) using the built-in 'Weight' predicate
2. Conditional header routing using a custom ConditionalHeaderPredicate

The second approach offers a consistent way of routing requests that ensures 
user affinity to the same version of the service to avoid inconsistent UX.

# Running the example
1. Run CreditCheckApiApplication.java
2. Run CanaryGatewayApplication.java
3. Run one of the examples in test-requests.http 

For the percentage based routing (/creditcheckpct), 90% of the requests should go to legacy API (response is LEGACY), the rest should go to modern API (response is MODERN)

For the conditional header routing, routing will depend on the custom header value

# Changing the configuration
Update the Weight configuration in canary-gateway/application.yaml to change the request distribution
