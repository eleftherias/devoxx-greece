package com.example.devoxx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/details").permitAll()
                        .requestMatchers(HttpMethod.POST, "/seat").hasAuthority("PREMIUM")
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
