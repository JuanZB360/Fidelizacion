package com.Fidelizacion.Registro_Marca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync 
public class RegistroMarcaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroMarcaApplication.class, args);
	}

}
