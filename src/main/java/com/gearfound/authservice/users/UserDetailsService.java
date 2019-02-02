package com.gearfound.authservice.users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Mono<UserDetails> userDetails = userRepository.findByEmail(email)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UserNotFoundException())))
                .map(UserDetails::new);
        return userDetails.block();
    }
}
