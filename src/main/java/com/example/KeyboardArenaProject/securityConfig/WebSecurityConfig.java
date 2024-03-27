package com.example.KeyboardArenaProject.securityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring().requestMatchers(toH2Console()).requestMatchers("/static/**","/login","/logout","/");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeHttpRequests(auth->auth.requestMatchers("/login","/board","/board/{board_id}","/signup").permitAll()
                                                            .anyRequest().authenticated())
                .formLogin(auth->auth.loginPage("/login").defaultSuccessUrl("/board"))
                .logout(auth->auth.logoutSuccessUrl("/board").invalidateHttpSession(true))
                .csrf(auth->auth.disable()).build();


    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
