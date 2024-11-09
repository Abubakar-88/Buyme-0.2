package com.buyme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.buyme.common.entity"})
public class BuymeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuymeBackEndApplication.class, args);
	}

}
