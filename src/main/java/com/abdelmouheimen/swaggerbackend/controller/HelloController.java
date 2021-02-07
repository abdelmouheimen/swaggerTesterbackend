package com.abdelmouheimen.swaggerbackend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdelmouheimen.swaggerbackend.service.WebServicesCaller;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class HelloController {
	
	@Autowired
	WebServicesCaller caller;
	
	@GetMapping("/")
	public String index() throws JsonParseException, JsonMappingException, IOException {
		
		caller.callWebServicesFromScenaris();
		
		return "Greetings from Spring Boot!";
	}

}