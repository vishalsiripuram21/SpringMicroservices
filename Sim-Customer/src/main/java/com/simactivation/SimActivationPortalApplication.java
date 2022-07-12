package com.simactivation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SimActivationPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimActivationPortalApplication.class, args);
	}

}
