package com.example.elibrary.config;

import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.security.JwtFilter;
import com.example.elibrary.service.serviceImpl.UserServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.elibrary.service.validator.AppStatusCode.VALIDATION_ERROR_CODE;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Lazy
    @Autowired
    UserServiceImpl userService;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    Gson gson;
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticationProvider());
    }
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);

        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/user").permitAll()
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/user/captcha").permitAll()
                .requestMatchers("/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //.exceptionHandling(e->e.authenticationEntryPoint(entryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
