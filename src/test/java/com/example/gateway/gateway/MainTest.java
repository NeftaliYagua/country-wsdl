package com.example.gateway.gateway;

import com.example.gateway.gateway.services.SoapService;
import com.example.gateway.wsdl.CountryNameResponse;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class MainTest {

    @Test
    public void contextLoads() {
    }
    @Test
    public void testCountryName() {
        SoapService soapService = new SoapService();
        CountryNameResponse result = soapService.clientCountryName("EC");
        assertEquals("Falló - El resultado para PE debe ser Peru", "Ecuador", result.getCountryNameResult());
    }
}
