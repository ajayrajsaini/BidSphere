package com.bidsphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth->auth
//                        .requestMatchers("/auth/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",                    // your auth endpoints
                                "/v3/api-docs/**",             // openapi json
                                "/swagger-ui.html",            // swagger html redirect
                                "/swagger-ui/**",              // swagger UI resources
                                "/webjars/**",                 // optional, if using webjars
                                "/v2/api-docs",                // for backward compatibility
                                "/swagger-resources/**",
                                "/auction/**"// old swagger config
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
