package org.imures.cashregister.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security{

    private final UserDetailsService userService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth
////                                        .requestMatchers("/login", "/token").permitAll()
                                        .requestMatchers("/").permitAll()
                                        .requestMatchers(GET, "/api/v1/user/view/**").hasRole("ADMIN")
                                        .requestMatchers("/api/v1/user/register").permitAll()
                                        .requestMatchers("/api/v1/user/authenticate").permitAll()
                                        .requestMatchers("/error").permitAll()
                                        .requestMatchers("api/v1/types/**").permitAll()
                                        .requestMatchers(POST,"api/v1/items/").hasRole("ADMIN")
                                        .requestMatchers(DELETE, "api/v1/items/**").hasRole("ADMIN")
                                        .requestMatchers(GET,"api/v1/items/**").permitAll()
                                        .requestMatchers(GET,"api/v1/items").permitAll()
                                        .anyRequest().authenticated()
//                                        .anyRequest().permitAll()
////                                        .requestMatchers("/account").hasRole("USER")
////                                        .requestMatchers("/admin-panel").hasRole("ADMIN")
////                                        .requestMatchers("/home").hasRole("USER")
////                                        .requestMatchers("/hello").permitAll()
////                                        .requestMatchers("/errors").permitAll()
////                                        .requestMatchers("/").permitAll()
                )
                .userDetailsService(userService)
                .formLogin(form ->
                        form.loginPage("/login")
                                .permitAll())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
