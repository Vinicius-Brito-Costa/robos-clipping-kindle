package com.lionfish.robo_clipping_kindle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RoboClippingKindleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoboClippingKindleApplication.class, args);
	}

}
