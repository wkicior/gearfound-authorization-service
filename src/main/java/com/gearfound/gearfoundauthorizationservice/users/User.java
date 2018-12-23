package com.gearfound.gearfoundauthorizationservice.users;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document
public class User {
    @Id
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private List<Role> roles;
}
