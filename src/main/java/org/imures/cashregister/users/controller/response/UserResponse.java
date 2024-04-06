package org.imures.cashregister.users.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.roles.response.RoleResponse;

@Getter
@Setter
public class UserResponse {
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private RoleResponse[] roles;

}