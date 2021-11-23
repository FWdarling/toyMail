package com.fourwood.toymall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@EnableDiscoveryClient
@SpringBootApplication
public class ToymallCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToymallCouponApplication.class, args);
	}

}
