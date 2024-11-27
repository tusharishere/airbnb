package com.airbnb.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
//        http.authorizeHttpRequests().requestMatchers("/api/v1/user/signup","/api/v1/user/signup-property-Owner")
//                                    .permitAll()
//                                    .requestMatchers("/api/v1/")
//                                    .hasAnyRole("OWNER","ADMIN")
//                                    .anyRequest()
//                                    .permitAll();
        return http.build();
    }
}
