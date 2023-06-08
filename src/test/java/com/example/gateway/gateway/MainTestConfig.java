package com.example.gateway.gateway;

import com.example.gateway.gateway.services.SoapService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MainTestConfig {
    @Primary
    @Bean
    SoapService soapService() {
        return new SoapService();
    }
}
