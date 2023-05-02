package com.example.devoxx;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/data")
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().hasAuthority("SCOPE_api:read")
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(withDefaults())
                );
        return http.build();
    }

    @Bean
    @Order(2)
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

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }
}
