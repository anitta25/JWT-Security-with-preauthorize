package com.example.jwtcorrect.config;

import com.example.jwtcorrect.Utility.JWTUtility;
import com.example.jwtcorrect.filter.JWTFilter;
import com.example.jwtcorrect.service.UserDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetService userDetService;
    @Autowired
    JWTFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder() throws  Exception
    {
        return  new BCryptPasswordEncoder();
    }
 @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception
 {
     httpSecurity                                   .csrf(AbstractHttpConfigurer::disable);
     httpSecurity                                   .authorizeHttpRequests((req)->req
             .requestMatchers("/signup","login").permitAll()
             .requestMatchers("/delete/{id}").hasAuthority("ROLE_ADMIN")
             .anyRequest().authenticated());
     httpSecurity      .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
     return  httpSecurity.build();
 }

@Bean
     public AuthenticationProvider authenticationProvider() throws  Exception
{
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider .setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetService);
    return  provider;
}
@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception
{
    return  authenticationConfiguration.getAuthenticationManager();
}
}
