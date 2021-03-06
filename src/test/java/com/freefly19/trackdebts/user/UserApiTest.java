package com.freefly19.trackdebts.user;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static com.freefly19.trackdebts.util.JwtClaimMatcher.hasClaim;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class UserApiTest {
    @LocalServerPort
    private int port;

    @Value("${app.secret}")
    private String secret;

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

    @Test
    @Sql(statements = "insert into users (id, email, password) values (7,'user1@gmail.com', '$2a$10$77exdjQuYemJmmUyC6Aax.4RLx68rbIYGNGx1koKT0Whrnk8eXtsK')")
    public void shouldLoginIntoSystem() {
        given()
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body("{\n" +
                    "  \"email\": \"user1@gmail.com\",\n" +
                    "  \"password\": \"password\"\n" +
                    "}")
        .when()
            .post("/api/users/token")
        .then()
            .statusCode(200)
            .content("token", hasClaim("id", equalTo(7)).withKey(secret))
            .content("token", hasClaim("email", equalTo("user1@gmail.com")).withKey(secret));
    }

}
