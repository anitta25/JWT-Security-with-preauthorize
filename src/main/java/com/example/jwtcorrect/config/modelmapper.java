package com.example.jwtcorrect.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class modelmapper {
    @Bean
    public ModelMapper modelMapper( ) throws  Exception
    {
        return  new ModelMapper();
    }
}
