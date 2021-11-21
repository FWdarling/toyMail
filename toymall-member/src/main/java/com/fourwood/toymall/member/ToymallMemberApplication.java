package com.fourwood.toymall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.fourwood.toymall.member.feign")
public class ToymallMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToymallMemberApplication.class, args);
	}

}
