package com.springBoot.MbakaraBlogApp.config2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig2 {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http.csrf().disable()
               .authorizeHttpRequests((authorize) ->
                       // authorize.anyRequest().authenticated()
                       authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                               .anyRequest().authenticated()
               ).httpBasic(Customizer.withDefaults());
       return http.build();
   }

   @Bean
   public UserDetailsService userDetailsService(){
       UserDetails glory = User.builder()
               .username("glory")
//               .password("glory") what is the difference using this line and below next line?
               .password(passwordEncoder().encode("glory"))
               .roles("USER")
               .build();

       // crating admin user
       UserDetails admin = User.builder()
               .username("admin")
//               .password("admin")
               .password(passwordEncoder().encode("admin"))
               .roles("ADMIN")
               .build();

       // Returning the instance of InMemoryDetailsManager.

       return new InMemoryUserDetailsManager(glory, admin);


   }
}
