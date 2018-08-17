package com.freefly19.trackdebts.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOk() throws Exception {
        mockMvc
                .perform(post("/users")
                        .content("{\n" +
                                "  \"email\": \"some@email.com\",\n" +
                                "  \"password\": \"MySuperPassword\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
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
}