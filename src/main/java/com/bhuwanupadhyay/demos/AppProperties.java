package com.bhuwanupadhyay.demos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String logPrefix;
    private String corsAllowOrigins;

    public String getLogPrefix() {
        return logPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public String getCorsAllowOrigins() {
        return corsAllowOrigins;
    }

    public void setCorsAllowOrigins(String corsAllowOrigins) {
        this.corsAllowOrigins = corsAllowOrigins;
    }
}
