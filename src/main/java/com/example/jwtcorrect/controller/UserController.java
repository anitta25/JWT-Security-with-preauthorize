package com.example.jwtcorrect.controller;

import com.example.jwtcorrect.Utility.JWTUtility;
import com.example.jwtcorrect.dto.LoginRequestDTO;
import com.example.jwtcorrect.dto.SignupRequestDTO;
import com.example.jwtcorrect.entity.User;
import com.example.jwtcorrect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    UserService userservice;
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO signupRequestDTO)
    {
       User user=  userservice.signup(signupRequestDTO);
       if (user==null)
            return       ResponseEntity.status(HttpStatus.CONFLICT).body("user already exists");
       else
       {
           Map<String,Object> response = new HashMap<>();
           response.put("message","successful registration");
           response.put("body", user);
           return  ResponseEntity.status(HttpStatus.OK).body(response);
       }


    }
    @GetMapping("/hello")
    public  String hello()
    {
        return "hello";
    }
    @PostMapping("/login")
    public  String login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword(),null);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            return jwtUtility.generateToken(loginRequestDTO.getUsername());
        }catch (AuthenticationException exception)
        {
            return  "login fail";
        }}
@DeleteMapping ("/delete/{id}")
        public String delete(@PathVariable Long id )
        {
            userservice.delete(id);
            return "user deleted";
        }


}
