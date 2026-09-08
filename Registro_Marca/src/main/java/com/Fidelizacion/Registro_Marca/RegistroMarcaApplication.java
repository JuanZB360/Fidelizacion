package com.Fidelizacion.Registro_Marca;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistroMarcaApplication {

    public static void main(String[] args) {
        // Carga el archivo .env si existe en la raíz
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        SpringApplication.run(RegistroMarcaApplication.class, args);
    }
}