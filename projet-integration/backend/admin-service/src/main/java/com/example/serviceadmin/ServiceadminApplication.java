package com.example.serviceadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServiceadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceadminApplication.class, args);
	}

}
