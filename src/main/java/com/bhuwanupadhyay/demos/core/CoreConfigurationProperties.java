package com.bhuwanupadhyay.demos.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "core")
public class CoreConfigurationProperties {

    public static final String DATA_SYSTEM_RESOLVER = "data-system-resolver";

    private String logPrefix;
    private String corsAllowOrigins;

    private DataSystemResolver dataSystemResolver = DataSystemResolver.JDBC;

    public DataSystemResolver getDataSystemResolver() {
        return dataSystemResolver;
    }

    public void setDataSystemResolver(DataSystemResolver dataSystemResolver) {
        this.dataSystemResolver = dataSystemResolver;
    }

    public String getLogPrefix() {
        return logPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public void setCorsAllowOrigins(String corsAllowOrigins) {
        this.corsAllowOrigins = corsAllowOrigins;
    }

    public String getCorsAllowOrigins() {
        return corsAllowOrigins;
    }

    public enum DataSystemResolver {
        JDBC;

        public static final String JDBC_RESOLVER = "jdbc";
    }
}
