package com.freefly19.trackdebts.user;

import com.spencerwi.either.Either;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnWorkFine() throws Exception {
        when(userService.registerUser(
                RegisterUserCommand.builder()
                        .email("some@email.com")
                        .password("MySuperPassword")
                        .build()
        )).thenReturn(
                Either.right(User.builder()
                        .id(17L)
                        .email("some@email.com")
                        .password("MySuperPassword")
                        .build())
        );

        mockMvc
                .perform(post("/users")
                        .content("{\n" +
                                "  \"email\": \"some@email.com\",\n" +
                                "  \"password\": \"MySuperPassword\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(17))
                .andExpect(jsonPath("$.email").value ("some@email.com"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }
    @Test
    public void shouldReturnBadRequestWithMessageWhenErrorComeFromService() throws Exception {
        when(userService.registerUser(any())).thenReturn(Either.left("Some Error"));

        mockMvc
                .perform(post("/users")
                        .content("{\n" +
                                "  \"email\": \"some@email.com\",\n" +
                                "  \"password\": \"MySuperPassword\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value("Some Error"));
    }

    @Test
    public void shouldReturnBadRequestIfEmailNotSpecified() throws Exception {
        mockMvc
                .perform(post("/users")
                        .content("{\"password\": \"MySuperPassword\"}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfPasswordNotSpecified() throws Exception {
        mockMvc
                .perform(post("/users")
                        .content("{\"email\": \"my.email@test.com\"}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfPasswordShortenThan8Symbols() throws Exception {
        mockMvc
                .perform(post("/users")
                        .content("{\n" +
                                "  \"email\": \"my.email@test.com\", \n" +
                                "  \"password\": \"_small_\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void shouldReturnBadRequestIfPasswordLongerThan255Symbols() throws Exception {
        mockMvc
                .perform(post("/users")
                        .content("{\n" +
                                "  \"email\": \"my.email@test.com\", \n" +
                                "  \"password\": \"" + RandomStringUtils.randomAlphabetic(256) + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfEmailIsInvalid() throws Exception {
        mockMvc
                .perform(post("/users")
                        .content("{\n" +
                                "  \"email\": \"my.email\", \n" +
                                "  \"password\": \"a strong password\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isBadRequest());
    }
}
