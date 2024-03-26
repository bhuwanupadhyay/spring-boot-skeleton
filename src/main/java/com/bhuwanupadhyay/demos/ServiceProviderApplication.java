package com.bhuwanupadhyay.demos;

import com.bhuwanupadhyay.demos.core.CoreBeanConfiguration;
import com.bhuwanupadhyay.demos.jdbc.JdbcBeanConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackageClasses = {CoreBeanConfiguration.class, JdbcBeanConfiguration.class})
@PropertySource("classpath:swagger.properties")
@SpringBootApplication
public class ServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApplication.class, args);
    }
}
