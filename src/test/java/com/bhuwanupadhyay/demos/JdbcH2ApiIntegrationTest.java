package com.bhuwanupadhyay.demos;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SuppressWarnings({"preview"})
class JdbcH2ApiIntegrationTest extends ApiIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.enabled", () -> true);
        registry.add(
                "spring.datasource.url",
                () ->
                        "jdbc:h2:mem:testdb;INIT=create domain if not exists jsonb as"
                                + " text;MODE=PostgreSQL");
    }
}
