package com.cybertronix.vehiclebooking.config;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cybertronix.vehiclebooking.common.enums.WellKnownUserRole;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


    //     http.csrf(AbstractHttpConfigurer::disable)
    //             .authorizeHttpRequests(request -> request.requestMatchers(
    //                             "/api/v1/user/register",  
    //                             "/v3/api-docs/**",
    //                             "/swagger-ui/**",
    //                             "/swagger-ui.html",
    //                             "/webjars/**")

    //                     .permitAll()
    //                     // .requestMatchers("/api/v1/admin/**").hasAuthority(Role.ADMIN.name())
    //                     // .requestMatchers("/api/v1/user/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())

    //                     .anyRequest().authenticated())

    //             .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authenticationProvider(authenticationProvider)
    //             .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/user/register").permitAll()
                        .requestMatchers("/api/v1/user/admin/**").hasAuthority("ROLE_" + WellKnownUserRole.ADMIN.getValue())
                        .requestMatchers("/api/v1/vehicle/**", "/api/v1/booking/**").hasAnyAuthority("ROLE_" + WellKnownUserRole.DRIVER.getValue(), "ROLE_" + WellKnownUserRole.ADMIN.getValue(), "ROLE_" + WellKnownUserRole.USER.getValue())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> 
                exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("message", "You do not have permission to access this resource!");
                    responseBody.put("timestamp", Instant.now().toString());
                    responseBody.put("dataSet", null);
                    responseBody.put("success", false);  

                    ObjectMapper objectMapper = new ObjectMapper();
                    response.getWriter().write(objectMapper.writeValueAsString(responseBody));
                })
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*"); // Allow all HTTP methods
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (e.g., cookies)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
