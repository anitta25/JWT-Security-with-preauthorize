package com.example.jwtcorrect.filter;

import com.example.jwtcorrect.Utility.JWTUtility;
import com.example.jwtcorrect.service.UserDetService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    UserDetService userDetService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("inside filter");
        String header= request.getHeader("authenticate");
        if (header!=null && header.startsWith("Bearer")) {
            String jwtToken = header.substring(7);

            String username = jwtUtility.extractUsername(jwtToken);
            UserDetails userDetails =  userDetService.loadUserByUsername(username);
            if (userDetails==null)
                log.info("no user found");
            else
            {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
                Authentication authentication=token;
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } filterChain.doFilter(request,response);

    }
}
