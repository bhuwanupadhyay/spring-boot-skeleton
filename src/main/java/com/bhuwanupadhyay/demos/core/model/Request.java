package com.bhuwanupadhyay.demos.core.model;

import java.time.Duration;

public record Request(
    String requestMethod, Duration latency, String requestUrl, int status, String ip) {}
