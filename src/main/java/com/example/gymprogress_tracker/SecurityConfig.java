package com.example.gymprogress_tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/about", "/fragments/**", "/css/**", "/js/**", "/videos/**").permitAll()
                        .requestMatchers("/dashboard/**").authenticated() // Beschermt dashboard en alle subroutes
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Logout endpoint
                        .logoutSuccessUrl("/") // Redirect to home page after logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete session cookies
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if ("user".equalsIgnoreCase(username)) { // Case-insensitive vergelijking
                return User.withUsername("user")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER")
                        .build();
            }
            throw new UsernameNotFoundException("Gebruiker niet gevonden: " + username);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
