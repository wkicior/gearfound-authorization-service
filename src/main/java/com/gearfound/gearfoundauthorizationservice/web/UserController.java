package com.gearfound.gearfoundauthorizationservice.web;

import com.gearfound.gearfoundauthorizationservice.users.User;
import com.gearfound.gearfoundauthorizationservice.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Valid User user) {
        return userService.addUser(user);
    }
}
