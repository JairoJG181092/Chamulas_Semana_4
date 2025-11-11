package com.chamulas.habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.chamulas.habitaciones", "com.chamulas.commons"})
public class MsvHabitacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvHabitacionesApplication.class, args);
	}

}
