package com.gearfound.authservice.web;

import com.gearfound.authservice.users.Role;
import com.gearfound.authservice.users.User;
import com.gearfound.authservice.users.UserInfo;
import com.gearfound.authservice.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @CrossOrigin(origins = "http://localhost:4200")
    public Mono<UserInfo> getUser(Principal principal) {
        return userService.getUserByName(principal.getName());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserInfo> saveUser(@RequestBody @Valid User user) {
        user.setRoles(Collections.singletonList(new Role("USER")));
        return userService.addUser(user);
    }
}
