package com.example.jwtcorrect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sampleuser")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username,password,role;


}
