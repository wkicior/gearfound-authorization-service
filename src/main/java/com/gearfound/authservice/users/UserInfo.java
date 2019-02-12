package com.gearfound.authservice.users;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode
public class UserInfo {
    String id;
    String email;
    List<Role> roles;
}
