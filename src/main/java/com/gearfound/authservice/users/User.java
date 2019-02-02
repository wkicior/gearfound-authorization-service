package com.gearfound.authservice.users;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document
@ToString(exclude = "password")
@EqualsAndHashCode
public class User {
    @Id
    private String id;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    private List<Role> roles;

    public static org.springframework.security.core.userdetails.UserDetails toUserDetails(User user) {
        return new UserDetails(user);
    }
}
