package com.gearfound.gearfoundauthorizationservice.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String PASSWORD = "KnightWhoSayNi";
    private static final String ENCODED_PASSWORD = "fnhafjheoh3ufoeafhjdskafh==";
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

    private User.UserBuilder anUser() {
        return User.builder();
    }
}