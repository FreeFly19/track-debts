package com.freefly19.trackdebts.user;

import com.freefly19.trackdebts.util.JwtClaimMatcher;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
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

    @Test
    @Sql(statements = "insert into user (id, email, password) values (7,'user1@gmail.com', '$2a$10$77exdjQuYemJmmUyC6Aax.4RLx68rbIYGNGx1koKT0Whrnk8eXtsK')")
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
            .content("token", new JwtClaimMatcher("id").equalTo(7))
            .content("token", new JwtClaimMatcher("email").equalTo("user1@gmail.com"));
    }

}
