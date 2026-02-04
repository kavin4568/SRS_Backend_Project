// package com.example.security;

// import com.example.security.security.JwtAuthenticationEntryPoint;
// import com.example.security.security.JwtAuthenticationFilter;
// import com.example.security.service.CustomUserDetailsService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import java.util.List;

// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity(prePostEnabled = true)
// @RequiredArgsConstructor
// public class SecurityConfig {

//     private final CustomUserDetailsService userDetailsService;
//     private final JwtAuthenticationEntryPoint authenticationEntryPoint;
//     private final JwtAuthenticationFilter jwtAuthenticationFilter;

//     /**
//      * Password encoder bean - uses BCrypt hashing
//      */
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();

//         configuration.setAllowedOriginPatterns(List.of("*")); // allow all origins
//         configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(List.of("*"));
//         configuration.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);

//         return source;
//     }

//     /**
//      * Authentication provider - connects UserDetailsService with PasswordEncoder
//      */
//     @Bean
//     public DaoAuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
// //        authProvider.setUserDetailsService();
//         authProvider.setPasswordEncoder(passwordEncoder());
//         return authProvider;
//     }

//     /**
//      * Authentication manager bean
//      */
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
//             throws Exception {
//         return authConfig.getAuthenticationManager();
//     }

//     /**
//      * Main security filter chain configuration
//      */
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 // Disable CSRF for stateless REST API
//                 .csrf(csrf -> csrf.disable())

//                 // Configure exception handling
//                 .exceptionHandling(exception -> exception
//                         .authenticationEntryPoint(authenticationEntryPoint))

//                 // Set session management to stateless
//                 .sessionManagement(session -> session
//                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//                 // Configure URL authorization
//                 .authorizeHttpRequests(auth -> auth
//                         // Public endpoints
//                         .requestMatchers("/api/auth/**").permitAll()
//                         .requestMatchers("/api/public/**").permitAll()
//                         .requestMatchers("/h2-console/**").permitAll()

//                         // Admin endpoints
//                         .requestMatchers("/api/admin/**").hasRole("ADMIN")

//                         // User endpoints
//                         .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")

//                         // All other requests need authentication
//                         .anyRequest().authenticated()
//                 )

//                 // Set authentication provider
//                 .authenticationProvider(authenticationProvider())

//                 // Add JWT filter before UsernamePasswordAuthenticationFilter
//                 .addFilterBefore(jwtAuthenticationFilter,
//                         UsernamePasswordAuthenticationFilter.class)

//                 // Allow frames for H2 console
//                 .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

//         return http.build();
//     }
// }