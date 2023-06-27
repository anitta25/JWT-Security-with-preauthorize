package com.example.jwtcorrect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class SignupRequestDTO {
    Long id;
    String username,password,role;
}
