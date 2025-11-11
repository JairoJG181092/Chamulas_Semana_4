package com.chamulas.reservaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.chamulas.reservaciones", "com.chamulas.commons"})
public class MsvReservacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvReservacionesApplication.class, args);
	}

}
