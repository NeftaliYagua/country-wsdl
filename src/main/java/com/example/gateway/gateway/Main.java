package com.example.gateway.gateway;

import com.example.gateway.gateway.services.SoapService;
import com.example.gateway.wsdl.CountryNameResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    CommandLineRunner lookup(SoapService soapService) {
        return args -> {
            String code = "PE";

            if (args.length > 0) {
                code = args[0];
            }

            CountryNameResponse response = soapService.clientCountryName(code);
            System.err.println("\r\n\n");
            System.err.println("Resultado para el código de país PE: " + response.getCountryNameResult());
        };
    }
}