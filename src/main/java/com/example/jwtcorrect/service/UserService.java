package com.example.jwtcorrect.service;

import com.example.jwtcorrect.Repository.UserRepo;
import com.example.jwtcorrect.dto.LoginRequestDTO;
import com.example.jwtcorrect.dto.SignupRequestDTO;
import com.example.jwtcorrect.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService  {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo userRepo;
    public User signup(SignupRequestDTO signupRequestDTO) {
        Optional< User> user=  Optional.ofNullable(userRepo.findByUsername(signupRequestDTO.getUsername()));
        if (user.isPresent())
            return  null;
        else
        {  User  newUser = new  User();
             modelMapper.map(signupRequestDTO,newUser);
             newUser.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
             userRepo.save(newUser);
             return newUser;
        }
    }

    public void login(LoginRequestDTO loginRequestDTO) {

    }


    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
