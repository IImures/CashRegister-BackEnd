package org.imures.cashregister.users.controller;

import lombok.RequiredArgsConstructor;
import org.imures.cashregister.users.controller.request.AuthenticationRequest;
import org.imures.cashregister.users.controller.request.UserRequest;
import org.imures.cashregister.users.controller.response.AuthenticationResponse;
import org.imures.cashregister.users.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser(
            @RequestBody UserRequest request
    ){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        return new ResponseEntity<>(userService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken
    ){
        return new ResponseEntity<>(userService.refresh(refreshToken.substring(7)), HttpStatus.OK);
    }

    @PostMapping("/valid")
    public ResponseEntity<Void> valid(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ){
        userService.validate(token.substring(7));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}