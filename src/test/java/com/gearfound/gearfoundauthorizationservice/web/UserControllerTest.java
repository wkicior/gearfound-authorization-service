package com.gearfound.gearfoundauthorizationservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearfound.gearfoundauthorizationservice.users.User;
import com.gearfound.gearfoundauthorizationservice.users.UserAlreadyExistsException;
import com.gearfound.gearfoundauthorizationservice.users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired private ObjectMapper mapper;

    @MockBean
    UserService userService;

    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isUnauthorized());
    }

    @Test
    void postUser() throws Exception {
        //given
        User user = User.builder()
                .email("some@test.pl")
                .password("mypass")
                .build();
        when(userService.addUser(any(User.class))).thenReturn(user);

        //when, then
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(user)));
    }

    @Test
    void postUserWhichAlreadyExists() throws Exception {
        //given
        User user = User.builder()
                .email("some@test.pl")
                .password("mypass")
                .build();
        when(userService.addUser(any(User.class))).thenThrow(new UserAlreadyExistsException("already exists"));

        //when, then
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isConflict());
    }

    @Test
    void postUserWithNotValidEmail() throws Exception {
        //given
        User user = User.builder()
                .email("some")
                .password("mypass")
                .build();

        //when, then
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}