package com.example.jwtcorrect.service;

import com.example.jwtcorrect.Repository.UserRepo;
import com.example.jwtcorrect.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetService  implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepo.findByUsername(username))
                .orElseThrow(()->new UsernameNotFoundException("username not found"));
        return  new CustomUserDetails(user);
    }
}
