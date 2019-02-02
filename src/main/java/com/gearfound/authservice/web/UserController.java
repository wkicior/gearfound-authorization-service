package com.gearfound.authservice.web;

import com.gearfound.authservice.users.Role;
import com.gearfound.authservice.users.User;
import com.gearfound.authservice.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Mono<Map<String, String>> getUser(Principal principal) {
        return userService.getUserByName(principal.getName()).flatMap(
                user -> Mono.just(getPrincipalForUser(user))
        );
    }

    Map<String, String> getPrincipalForUser(User user) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", user.getEmail());
        map.put("id", user.getId());
        return map;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody @Valid User user) {
        user.setRoles(Collections.singletonList(new Role("USER")));
        return userService.addUser(user);
    }
}
