package com.gearfound.authservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearfound.authservice.users.User;
import com.gearfound.authservice.users.UserAlreadyExistsException;
import com.gearfound.authservice.users.UserInfo;
import com.gearfound.authservice.users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestSecurityConfiguration.class, UserController.class, ExceptionHandler.class})
class UserControllerTest {

    @Autowired
    protected WebTestClient webClient;

    @Autowired private ObjectMapper mapper;

    @MockBean
    UserService userService;

    @MockBean
    TokenStore tokenStore;

    @Test
    void getUserNotAuthorized() throws Exception {
        webClient.get().uri("/user")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void postUser() throws Exception {
        //given
        User user = User.builder()
                .email("some@test.pl")
                .password("mypass")
                .build();
        when(userService.addUser(any(User.class))).thenReturn(Mono.just(user.toUserInfo()));

        //when, then
        webClient.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserInfo.class)
                .isEqualTo(user.toUserInfo());
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
        webClient.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void postUserWithNotValidEmail() throws Exception {
        //given
        User user = User.builder()
                .email("some")
                .password("mypass")
                .build();

        //when, then
        webClient.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user))
                .exchange()
                .expectStatus().isBadRequest();
    }
}