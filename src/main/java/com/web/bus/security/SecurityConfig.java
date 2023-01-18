package com.web.bus.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Security configuration class for the application.
 * This class is responsible for providing beans for various security related components such as PasswordEncoder
 */
@Configuration
public class SecurityConfig {
    /**
     * Bean for providing BCryptPasswordEncoder
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

