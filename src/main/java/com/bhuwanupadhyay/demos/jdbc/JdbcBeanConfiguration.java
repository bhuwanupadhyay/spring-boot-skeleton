package com.bhuwanupadhyay.demos.jdbc;

import static com.bhuwanupadhyay.demos.core.CoreConfigurationProperties.DATA_SYSTEM_RESOLVER;
import static com.bhuwanupadhyay.demos.core.CoreConfigurationProperties.DataSystemResolver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = DATA_SYSTEM_RESOLVER,
        havingValue = DataSystemResolver.JDBC_RESOLVER,
        matchIfMissing = true)
public class JdbcBeanConfiguration {

    @Bean
    public JdbcEntityRepository jdbcEntityRepository() {
        return new JdbcEntityRepository();
    }
}
