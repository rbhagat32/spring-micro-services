package com.rbhagat32.userservice.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // String -> Instant converter
        Converter<String, Instant> stringToInstant = ctx -> {
            String source = ctx.getSource();
            return (source == null || source.isBlank()) ? null : Instant.parse(source);
        };

        mapper.addConverter(stringToInstant, String.class, Instant.class);
        return mapper;
    }
}