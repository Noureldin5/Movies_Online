package org.example.midterm.config;

    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
    import org.springframework.security.web.SecurityFilterChain;

    import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
    @RequiredArgsConstructor
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**", "/register/**").permitAll()
                    .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
            return http.build();
        }
    }