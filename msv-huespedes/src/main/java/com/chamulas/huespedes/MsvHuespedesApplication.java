package com.chamulas.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.chamulas.huespedes", "com.chamulas.commons"})
public class MsvHuespedesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvHuespedesApplication.class, args);
	}

}
//AVISO
