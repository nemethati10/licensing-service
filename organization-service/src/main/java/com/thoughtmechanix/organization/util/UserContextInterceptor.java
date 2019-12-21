package com.thoughtmechanix.organization.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Used to inject the correlation ID into any outgoing HTTP-based service
 * requests being executed from a RestTemplate instance. This is done to ensure
 * that you can establish a linkage between service calls.
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());

        return execution.execute(request, body);
    }
}
