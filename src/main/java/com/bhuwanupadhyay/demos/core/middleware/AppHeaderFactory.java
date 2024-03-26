package com.bhuwanupadhyay.demos.core.middleware;

import com.bhuwanupadhyay.demos.core.model.AppHeaders;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequestScope
public class AppHeaderFactory extends AppHeaders {

    @Autowired
    public AppHeaderFactory(HttpServletRequest request) {

        Map<String, String> headers =
                Collections.list(request.getHeaderNames()).stream()
                        .collect(Collectors.toMap(h -> h, request::getHeader));

        this.addFromMap(headers);

        // Add Correlation ID if missing
        this.addCorrelationIdIfMissing();
    }
}
