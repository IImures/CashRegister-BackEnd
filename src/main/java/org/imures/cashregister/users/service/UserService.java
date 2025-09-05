package org.imures.cashregister.users.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imures.cashregister.configuration.JwtService;
import org.imures.cashregister.roles.entity.Role;
import org.imures.cashregister.roles.repository.RoleRepository;
import org.imures.cashregister.users.controller.request.AuthenticationRequest;
import org.imures.cashregister.users.controller.request.UserRequest;
import org.imures.cashregister.users.controller.response.AuthenticationResponse;
import org.imures.cashregister.users.entity.User;
import org.imures.cashregister.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse createUser(UserRequest details) {
        boolean isExists = true;
        try {
            userRepository.findUserByEmail(details.getEmail())
                    .orElseThrow(() -> new RuntimeException("User does not exists"));
        } catch (RuntimeException e) {
            isExists = false;
        }
        if (isExists) throw new RuntimeException("User already exists with this login");

        User newUser = new User();

        BeanUtils.copyProperties(details, newUser);
        newUser.setIsEnabled(true);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (Long roleId : details.getRoles()) {
            roles.add(
                    roleRepository
                            .findById(roleId)
                            .orElseThrow(() -> new EntityNotFoundException("Role " + roleId + " does not exists!"))
            );
        }

        newUser.setRoles(roles);

        User saved = userRepository.save(newUser);
        String jwtToken = jwtService.generateToken(saved);
        String refreshToken = jwtService.generateRefreshToken(saved);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        List<String> roles = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        String token = jwtService.generateToken(claims, (UserDetails) auth.getPrincipal());
        String refreshToken = jwtService.generateRefreshToken((UserDetails) auth.getPrincipal());

        log.info("User authenticated: {} ", ((UserDetails) auth.getPrincipal()).getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse refresh(String refreshToken) {
        String userMail = jwtService.getRefreshSubject(refreshToken);
        User user = userRepository.findUserByEmail(userMail)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exists"));

        if (!jwtService.validateJwtRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Invalid token");
        }

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        String token = jwtService.generateToken(claims, user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        log.info("User refreshed: {} ", user.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void validate(String token) {
        if (!jwtService.validateJwtToken(token)) {
            log.warn("User not authenticated: {}", jwtService.getSubject(token));
            throw new BadCredentialsException("Invalid token");
        }
        log.info("User validated: {} ", jwtService.getSubject(token));
    }
}