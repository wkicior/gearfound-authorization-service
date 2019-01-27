package com.gearfound.gearfoundauthorizationservice.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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
    void addUser() {
        //given
        User user = anUser().password(PASSWORD).build();
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(user)).thenReturn(user);

        //when
        User savedUser = userService.addUser(user);

        //then
        assertEquals(ENCODED_PASSWORD, savedUser.getPassword());
    }

    @Test
    void addUserThrowsExceptionWhenUserAlreadyExists() {
        //given
        User user = anUser().email(EMAIL).password(PASSWORD).build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        //when, then
        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(user));

    }

    @Test
    void getUserByEmail() {
        //given
        User user = anUser().email(EMAIL).build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        //when
        User returnedUser = userService.getUserByName(EMAIL);

        //when
        assertThat(returnedUser).isEqualTo(user);
    }

    @Test
    void getUserByEmailThrosUserNotFoundException() {
        //given
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        //when, then
        assertThrows(UserNotFoundException.class, () -> userService.getUserByName(EMAIL));
    }

    private User.UserBuilder anUser() {
        return User.builder();
    }
}