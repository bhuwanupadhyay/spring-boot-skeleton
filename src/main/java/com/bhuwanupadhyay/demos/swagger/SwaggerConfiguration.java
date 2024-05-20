package com.bhuwanupadhyay.demos.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import jakarta.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;

@Configuration
@PropertySource("classpath:swagger-common.properties")
public class SwaggerConfiguration {

    private final SwaggerConfigurationProperties configurationProperties;

    public SwaggerConfiguration(SwaggerConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    @Bean
    public OpenAPI openApi(ServletContext servletContext) {
        Server server = new Server().url(servletContext.getContextPath());
        OpenAPI openAPI =
                new OpenAPI()
                        .info(
                                new Info()
                                        .title(configurationProperties.getApiTitle())
                                        .description(configurationProperties.getApiDescription())
                                        .version(configurationProperties.getApiVersion())
                                        .contact(
                                                new Contact()
                                                        .name(
                                                                configurationProperties
                                                                        .getApiContactName())
                                                        .email(
                                                                configurationProperties
                                                                        .getApiContactEmail()))
                                        .license(
                                                new License()
                                                        .name(
                                                                configurationProperties
                                                                        .getApiLicenseName())
                                                        .url(
                                                                configurationProperties
                                                                        .getApiLicenseUrl())))
                        .components(
                                new Components()
                                        .addSecuritySchemes(
                                                "Authorization",
                                                new SecurityScheme()
                                                        .type(SecurityScheme.Type.HTTP)
                                                        .scheme("bearer")
                                                        .bearerFormat("Authorization")
                                                        .in(SecurityScheme.In.HEADER)
                                                        .name("Authorization")))
                        .addSecurityItem(new SecurityRequirement().addList("Authorization"));
        return openAPI.servers(Collections.singletonList(server));
    }
}
