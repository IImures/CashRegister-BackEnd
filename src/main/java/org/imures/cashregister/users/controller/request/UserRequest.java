package org.imures.cashregister.users.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private Long[] roles;

}