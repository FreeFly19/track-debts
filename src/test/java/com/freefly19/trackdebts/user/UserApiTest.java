package com.freefly19.trackdebts.user;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiTest {
    @LocalServerPort
    private int port;

    @Before
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    public void shouldSuccessfullyCreateNewUser() {
        given()
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body("{\n" +
                    "  \"email\": \"email@test.com\",\n" +
                    "  \"password\": \"somepassword\"\n" +
                    "}")
        .when()
            .post("/api/users")
        .then()
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .statusCode(200)
            .content("id", isA(Number.class))
            .content("email", equalTo("email@test.com"))
            .content("password", nullValue());

    }
}
