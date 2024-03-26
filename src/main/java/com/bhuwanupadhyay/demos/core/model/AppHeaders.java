package com.bhuwanupadhyay.demos.core.model;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class AppHeaders {
    public static final String CORRELATION_ID = "correlation-id";
    public static final String AUTHORIZATION = "authorization";
    public static final String CONTENT_TYPE = "content-type";
    public static final String USER = "user";
    private static final HashSet<String> headerKeys = new HashSet<>();

    static {
        headerKeys.add(CORRELATION_ID);
        headerKeys.add(AUTHORIZATION);
        headerKeys.add(USER);
        headerKeys.add(CONTENT_TYPE);
    }

    private final Map<String, String> headers = new HashMap<>();

    public AppHeaders() {
        this.headers.put(CONTENT_TYPE, "application/json");
    }

    public static Map<String, String> createStandardLabelsFromMap(Map<String, String> input) {
        Map<String, String> output = new HashMap<>();
        if (input != null) {
            input.forEach(
                    (k, v) -> {
                        String key = k.toLowerCase();
                        if (headerKeys.contains(key)) output.put(key, v);
                    });
        }
        return output;
    }

    protected void addFromMap(Map<String, String> input) {
        input.forEach(
                (k, v) -> {
                    String key = k.toLowerCase();
                    if (headerKeys.contains(key) || key.startsWith("x-")) {
                        this.headers.put(key, v);
                    }
                });
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getCorrelationId() {
        return this.getHeader(CORRELATION_ID);
    }

    public String getAuthorization() {
        return this.getHeader(AUTHORIZATION);
    }

    public String getContentType() {
        return this.getHeader(CONTENT_TYPE);
    }

    public String getUser() {
        return this.getHeader(USER);
    }

    public void put(String key, String value) {
        this.headers.put(key, value);
    }

    public void addCorrelationIdIfMissing() {
        if (StringUtils.isBlank(this.getCorrelationId())) {
            this.headers.put(CORRELATION_ID, UUID.randomUUID().toString());
        }
    }

    private String getHeader(String key) {
        return this.headers.get(key.toLowerCase());
    }
}
