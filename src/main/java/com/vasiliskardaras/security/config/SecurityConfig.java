package com.vasiliskardaras.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
                .and()
                .authorizeRequests()
//                    .anyRequest().authenticated() // endpoint level authorization
//                .anyRequest().permitAll()
//                .anyRequest().hasAuthority("read")
//                .anyRequest().hasAnyAuthority("read", "write")
//                .anyRequest().access("isAuthenticated() and hasAuthority('read')") // SpEL --> authorization rules
                .requestMatchers("/demo").hasAuthority("read")
                .and().build();


        // mathcer method + authorization rule
        // 1. which matcher methods should you use and how ( anyRequest(), mvcMatchers(), antMatchers(), regexMatchers() )
        // 2. how to apply different authorization rules
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        var u2 = User.withUsername("john")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();

        uds.createUser(u1);
        uds.createUser(u2);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
