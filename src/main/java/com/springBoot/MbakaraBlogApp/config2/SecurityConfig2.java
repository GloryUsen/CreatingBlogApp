package com.springBoot.MbakaraBlogApp.config2;

import com.springBoot.MbakaraBlogApp.security.ConsumerUserDetailsService;
import com.springBoot.MbakaraBlogApp.security.JwtAuthenticationEntryPoint;
import com.springBoot.MbakaraBlogApp.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class SecurityConfig2 {

    private final ConsumerUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig2(ConsumerUserDetailsService userDetailsService,
                           JwtAuthenticationEntryPoint authenticationEntryPoint,
                           JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider using custom UserDetailService

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Old one for spring 2

        //DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //authProvider.setUserDetailsService(userDetailsService);
        //authProvider.setPasswordEncoder(passwordEncoder());
       // return authProvider;

       // New one for spring 3 + spring security 6 + SecurityFilterChain
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(userDetailsService);
       provider.setPasswordEncoder(passwordEncoder());
       return provider;
    }

    // AuthenticationManager bean for login/authentication usage
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Security Filter Chain configuration

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())

        // Exception Handling for unauthorise access
        .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))

        // Stateless session management (no HTTP session)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Authoriation Rules
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/api/**").permitAll() //Anyone can get posts
                    .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll() // Categories public
                    .requestMatchers("/api/auth/**").permitAll() // Registration, Login are public.
                    .requestMatchers(HttpMethod.POST, "/api/posts/**").authenticated() // Requires login
                    .requestMatchers("/swagger-ui/**").permitAll() // swagger ui public
                    .requestMatchers("/v3/api-docs/**").permitAll() // swagger doc public
                    .anyRequest().authenticated() // Every Other thing requires authentication
            // )
            // .exceptionHandling(exception -> exception
            //         .authenticationEntryPoint(authenticationEntryPoint)
            // )
            // .sessionManagement(session -> session
            //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

            // Adding JWT filter before spring Security's UsernamePasswordAuthenticationFilter

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
