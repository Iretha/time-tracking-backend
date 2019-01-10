package com.smdev.timetracking.ttbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smdev.timetracking.ttbackend.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserController controller;

    @Test
    public void getUserById() throws Exception {
        User user = new User();
        user.setId(2);
        user.setName("Alex");

        ResponseEntity<User> response = ResponseEntity.ok(user);
        given(controller.getUserById(user.getId())).willReturn(response);

        this.mvc
                .perform(get("/api/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is((int) user.getId())));
    }

    @Test
    public void createUser() throws Exception {
        User alex = new User();
        alex.setId(2);
        alex.setName("Alex");

        given(controller.createUser(Mockito.any())).willReturn(alex);

        String userJson = mapper.writeValueAsString(alex);
        this.mvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON_UTF8)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(alex.getName())));
    }
}
