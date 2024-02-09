package com.cic.incidencias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cic.incidencias")
public class IncidenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidenciasApplication.class, args);
	}

}
