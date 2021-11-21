package com.fourwood.toymall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ToymallOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToymallOrderApplication.class, args);
	}

}
