package com.github.aleksanderkot00.onlinesportsbetting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderFactory {

    @Bean
    public PasswordEncoder makePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
