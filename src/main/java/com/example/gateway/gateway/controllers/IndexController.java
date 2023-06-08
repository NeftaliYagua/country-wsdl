package com.example.gateway.gateway.controllers;

import com.example.gateway.gateway.services.SoapService;
import com.example.gateway.wsdl.CountryNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    SoapService soapService;
    @GetMapping("/")
    public ResponseEntity<String> index() {
        CountryNameResponse result = soapService.clientCountryName("CO");
        return new ResponseEntity<String>(result.getCountryNameResult(), HttpStatus.OK);
    }
}
