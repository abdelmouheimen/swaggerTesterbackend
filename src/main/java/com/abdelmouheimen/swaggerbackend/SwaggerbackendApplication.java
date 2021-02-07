package com.abdelmouheimen.swaggerbackend;


import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abdelmouheimen.swaggerbackend.service.WebServicesCaller;

@SpringBootApplication
public class SwaggerbackendApplication  implements CommandLineRunner{

	@Autowired
	WebServicesCaller caller;
	
	public static void main(String[] args) {
		SpringApplication.run(SwaggerbackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		caller.mainService();		
	//	assertTrue(false);
	}

}
