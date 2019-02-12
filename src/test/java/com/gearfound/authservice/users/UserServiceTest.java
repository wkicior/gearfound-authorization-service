package com.gearfound.authservice.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String PASSWORD = "KnightWhoSayNi";
    private static final String ENCODED_PASSWORD = "fnhafjheoh3ufoeafhjdskafh==";
    private static final String EMAIL = "test@wp.pl";
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void addUserSavesUserAndReturnsUserInfo() {
        //given
        User user = anUser().email(EMAIL).password(PASSWORD).build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Mono.empty());
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        //when
        Mono<UserInfo> savedUser = userService.addUser(user);

        //then
        assertEquals(EMAIL, savedUser.block().getEmail());
    }

    @Test
    void addUserNormalizesUserBeforeSave() {
        //given
        User user = anUser().email(EMAIL.toUpperCase()).password(PASSWORD).build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Mono.empty());
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        //when
        Mono<UserInfo> savedUser = userService.addUser(user);

        //then
        assertEquals(EMAIL.toLowerCase(), savedUser.block().getEmail());
    }

    @Test
    void addUserThrowsExceptionWhenUserAlreadyExists() {
        //given
        User user = anUser().email(EMAIL).password(PASSWORD).build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Mono.just(user));

        //when
        Mono<UserInfo> result = userService.addUser(user);

        //then
        assertThrows(UserAlreadyExistsException.class, result::block);

    }

    @Test
    void getUserByEmailReturnsUserByUsername() {
        //given
        User user = anUser().email(EMAIL).build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Mono.just(user));

        //when
        Mono<UserInfo> returnedUser = userService.getUserByName(EMAIL);

        //when
        assertThat(returnedUser.block()).isEqualTo(user.toUserInfo());
    }

    @Test
    void getUserByEmailReturnsUserByUsernameForUppercaseQuery() {
        //given
        User user = anUser().email(EMAIL.toLowerCase()).build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Mono.just(user));

        //when
        Mono<UserInfo> returnedUser = userService.getUserByName(EMAIL.toUpperCase());

        //when
        assertThat(returnedUser.block()).isEqualTo(user.toUserInfo());
    }

    @Test
    void getUserByEmailThrowsUserNotFoundException() {
        //given
        when(userRepository.findByEmail(EMAIL)).thenReturn(Mono.empty());

        //when
        Mono<UserInfo> result = userService.getUserByName(EMAIL);

        //then
        assertThrows(UserNotFoundException.class, result::block);
    }

    private User.UserBuilder anUser() {
        return User.builder();
    }
}