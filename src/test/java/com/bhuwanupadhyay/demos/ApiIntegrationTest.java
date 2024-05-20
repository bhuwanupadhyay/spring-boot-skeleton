package com.bhuwanupadhyay.demos;

import com.bhuwanupadhyay.demos.model.EntityProperties;
import com.bhuwanupadhyay.demos.model.Property;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import jakarta.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    void verifyEntityApi() {
        Map<String, Property> fakeData = new HashMap<>();
        fakeData.put("key", new Property("sub_key", "sub_value"));
        EntityProperties fakeBody = new EntityProperties(fakeData);

        given()
                .contentType(JSON)
                .when()
                .body(fakeBody)
                .post()
                .then()
                .statusCode(201)
                .body(".", notNullValue());


        given()
                .contentType(JSON)
                .when()
                .param("query", "")
                .param("pageNumber", 1)
                .param("pageSize", 10)
                .get()
                .then()
                .statusCode(200)
                .body(".", notNullValue());
    }


}
