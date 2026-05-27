package com.terraviva.config;

import com.terraviva.security.CustomUserDetailsService;
import com.terraviva.security.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/error").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/habitaciones/**").hasAnyRole("ADMIN", "HUESPED")
                        .requestMatchers(HttpMethod.POST, "/api/habitaciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/habitaciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/habitaciones/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/reservas/**").hasAnyRole("ADMIN", "HUESPED")
                        .requestMatchers(HttpMethod.POST, "/api/reservas/**").hasAnyRole("ADMIN", "HUESPED")
                        .requestMatchers(HttpMethod.PUT, "/api/reservas/**").hasAnyRole("ADMIN", "HUESPED")
                        .requestMatchers(HttpMethod.DELETE, "/api/reservas/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())
                        )
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}