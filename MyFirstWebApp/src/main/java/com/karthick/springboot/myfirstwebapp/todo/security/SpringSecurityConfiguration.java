package com.karthick.springboot.myfirstwebapp.todo.security;

import org.springframework.context.annotation.Configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import java.util.function.Function;

@Configuration
public class SpringSecurityConfiguration {


    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager(){

        UserDetails userDetails = createNewUser("karthick","no");
        UserDetails userDetails1 = createNewUser("l","m");
        return new InMemoryUserDetailsManager(userDetails, userDetails1);
    }

    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder =
                input -> passwordEncoder().encode(input);

        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER","ADMIN")
                .build();
        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
            httpSecurity.authorizeHttpRequests(
                    auth -> auth.anyRequest().authenticated());
            httpSecurity.formLogin(withDefaults());
            httpSecurity.csrf().disable();
            httpSecurity.headers().frameOptions().disable();
            return httpSecurity.build();
    }
}
