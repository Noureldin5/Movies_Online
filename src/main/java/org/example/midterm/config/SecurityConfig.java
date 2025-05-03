package org.example.midterm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2AuthSuccessHandler oAuth2AuthSuccessHandler;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**", "/register/**", "/oauth2/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(endpoint -> endpoint
                    .baseUri("/oauth2/authorize")
                )
                .redirectionEndpoint(endpoint -> endpoint
                    .baseUri("/oauth2/callback/*")
                )
                .userInfoEndpoint(endpoint -> endpoint
                    .userService(oAuth2UserService)
                )
                .successHandler(oAuth2AuthSuccessHandler)
            );
        
        // To allow H2 console
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        
        return http.build();
    }
}