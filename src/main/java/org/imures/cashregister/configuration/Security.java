package org.imures.cashregister.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class Security{

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/api/v1/user/register")
                                        .hasRole("ADMIN")
                                        .requestMatchers("/api/v1/user/**")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/catalog/**")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/sub-catalog/**")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/producer/**")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/product/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
