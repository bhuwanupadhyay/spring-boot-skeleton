package com.bhuwanupadhyay.demos;

import io.restassured.RestAssured;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

@SuppressWarnings({"preview"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public abstract class ApiIntegrationTest {

    @LocalServerPort
    protected Integer port;

    @Autowired
    protected ServletContext servletContext;

    @Value("${serviceApi.path}")
    protected String serviceApiPath;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = STR."http://localhost:\{port}\{servletContext.getContextPath()}\{serviceApiPath}";
    }

    @Test
    void verifySearchApi() {
        given().contentType(JSON).when().param("query", "").param("page", 0).param("pageSize", 10).get().then().statusCode(200).body(".", notNullValue());
    }


}
