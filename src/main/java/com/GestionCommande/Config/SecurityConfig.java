package com.GestionCommande.Config;


import com.GestionCommande.Security.JwtAuthentificationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {

        private JwtAuthentificationFilter jwtAuthentificationFilter;

        public SecurityConfig(JwtAuthentificationFilter jwtAuthentificationFilter) {
                this.jwtAuthentificationFilter = jwtAuthentificationFilter;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())
                        .addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

//        @Bean
//        public PasswordEncoder passwordEncoder() {
//                return new BCryptPasswordEncoder();
//        }

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }
}
