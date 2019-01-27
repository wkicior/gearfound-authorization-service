package com.gearfound.gearfoundauthorizationservice.web;

import com.gearfound.gearfoundauthorizationservice.users.Role;
import com.gearfound.gearfoundauthorizationservice.users.User;
import com.gearfound.gearfoundauthorizationservice.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, String> getUser(Principal principal) {
        User user = userService.getUserByName(principal.getName());
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        map.put("id", user.getId());
        return map;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Valid User user) {
        user.setRoles(Collections.singletonList(new Role("USER")));
        return userService.addUser(user);
    }
}
