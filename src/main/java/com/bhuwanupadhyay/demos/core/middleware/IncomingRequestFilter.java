package com.bhuwanupadhyay.demos.core.middleware;

import com.bhuwanupadhyay.demos.core.CoreConfigurationProperties;
import com.bhuwanupadhyay.demos.core.model.AppHeaders;
import com.bhuwanupadhyay.demos.core.model.AppLogger;
import com.bhuwanupadhyay.demos.core.model.Request;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class IncomingRequestFilter implements Filter {

    private static final Set<String> ignoredUris = Set.of("/swagger", "/v1/api-docs", "/webjars/");
    private final AppHeaders headers;
    private final AppLogger logger;
    private final String allowOriginDomains;

    public IncomingRequestFilter(
            AppHeaders headers,
            AppLogger logger,
            CoreConfigurationProperties configurationProperties) {
        this.headers = headers;
        this.logger = logger;
        this.allowOriginDomains = configurationProperties.getCorsAllowOrigins();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        headers.addCorrelationIdIfMissing();
        long startTime = System.currentTimeMillis();
        setResponseHeaders(httpServletResponse);

        try {
            if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
                httpServletResponse.setStatus(200);
            } else {
                chain.doFilter(request, response);
            }
        } finally {
            logRequest(httpServletRequest, httpServletResponse, startTime);
        }
    }

    private void logRequest(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            long startTime) {
        String uri = this.getUri(servletRequest);
        if (!ignoredUris.contains(uri)) {
            Request request =
                    new Request(
                            servletRequest.getMethod(),
                            Duration.ofMillis(System.currentTimeMillis() - startTime),
                            uri,
                            servletResponse.getStatus(),
                            servletRequest.getRemoteAddr());
            this.logger.request(request);
        }
    }

    private void setResponseHeaders(HttpServletResponse httpServletResponse) {
        Map<String, String> responseHeaders = this.getResponseHeaders(allowOriginDomains);
        for (Map.Entry<String, String> header : responseHeaders.entrySet()) {
            if ("Cache-Control".equalsIgnoreCase(header.getKey())) {
                httpServletResponse.addHeader(header.getKey(), "private, max-age=300");
            } else {
                httpServletResponse.addHeader(header.getKey(), header.getValue());
            }
        }
        httpServletResponse.addHeader(AppHeaders.CORRELATION_ID, this.headers.getCorrelationId());
    }

    private String getUri(HttpServletRequest httpServletRequest) {
        StringBuilder requestURL = new StringBuilder(httpServletRequest.getRequestURL().toString());
        String queryString = httpServletRequest.getQueryString();
        return queryString == null
                ? requestURL.toString()
                : requestURL.append('?').append(queryString).toString();
    }

    private Map<String, String> getResponseHeaders(String commaDelimitedDomains) {
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Access-Control-Allow-Origin", commaDelimitedDomains);
        responseHeaders.put("Access-Control-Allow-Credentials", "true");
        responseHeaders.put(
                "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        responseHeaders.put("X-Frame-Options", "DENY");
        responseHeaders.put("X-XSS-Protection", "1; mode=block");
        responseHeaders.put("X-Content-Type-Options", "nosniff");
        responseHeaders.put("Cache-Control", "no-cache, no-store, must-revalidate");
        responseHeaders.put("Content-Security-Policy", "default-src 'self'");
        responseHeaders.put("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        responseHeaders.put("Expires", "0");
        responseHeaders.put("Access-Control-Max-Age", "3600");
        responseHeaders.put(
                "Access-Control-Allow-Headers",
                "access-control-allow-origin, origin, content-type, accept, authorization,"
                        + " data-partition-id, correlation-id, appkey");
        return responseHeaders;
    }
}
