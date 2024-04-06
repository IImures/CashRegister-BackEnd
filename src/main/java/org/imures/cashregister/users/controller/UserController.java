package org.imures.cashregister.users.controller;

import lombok.RequiredArgsConstructor;
import org.imures.cashregister.users.controller.request.AuthenticationRequest;
import org.imures.cashregister.users.controller.request.UserRequest;
import org.imures.cashregister.users.controller.response.AuthenticationResponse;
import org.imures.cashregister.users.controller.response.UserResponse;
import org.imures.cashregister.users.service.UserService;
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

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return new ResponseEntity<>(userService.authenticate(request), HttpStatus.OK);
    }

    @GetMapping("view/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Long userId
    ){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

}