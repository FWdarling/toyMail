package com.fourwood.toymall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ToymallWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToymallWareApplication.class, args);
	}

}
