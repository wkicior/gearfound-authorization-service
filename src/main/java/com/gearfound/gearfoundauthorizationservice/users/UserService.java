package com.gearfound.gearfoundauthorizationservice.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Mono<User> addUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existingUser -> Mono.<User>error(new UserAlreadyExistsException(existingUser.getEmail())))
                .switchIfEmpty(encodePasswordAndSaveUser(user));

    }

    private Mono<User> encodePasswordAndSaveUser(User user) {
        return Mono.defer(() -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return this.userRepository.save(user);
        });
    }


    public Mono<User> getUserByName(String name) {
        return userRepository.findByEmail(name).switchIfEmpty(Mono.defer(() -> Mono.error(new UserNotFoundException())));
    }
}
